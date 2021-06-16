package com.gyh.keepaccounts.service

import com.github.pagehelper.PageHelper
import com.gyh.keepaccounts.common.firstDay
import com.gyh.keepaccounts.common.lastDay
import com.gyh.keepaccounts.common.toLocalDateTime
import com.gyh.keepaccounts.mapper.BillMapper
import com.gyh.keepaccounts.mapper.PurchaseMapper
import com.gyh.keepaccounts.model.Bill
import com.gyh.keepaccounts.model.PageView
import com.gyh.keepaccounts.model.view.BillRequestInfo
import com.gyh.keepaccounts.model.view.BillResponseInfo
import org.apache.poi.hssf.usermodel.HSSFDataFormat
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.springframework.stereotype.Service
import java.io.File
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import javax.annotation.Resource

/**
 * Created by gyh on 2021/5/31
 */
@Service
class BillService {
    @Resource
    lateinit var billMapper: BillMapper

    @Resource
    lateinit var purchaseMapper: PurchaseMapper

    fun createBill(bill: BillRequestInfo): Int {
        if (!bill.checkPaymentType()) error("付款类型应为wx：微信；zfb：支付宝；rmb：现金；wzf：未支付")
        return billMapper.batchInsert(bill.createBill())
    }

    fun updateBill(bill: Bill): Int {
        if (!bill.checkPaymentType()) error("付款类型应为wx：微信；zfb：支付宝；rmb：现金；wzf：未支付")
        bill.payment =
            if (bill.paymentType == "wzf") BigDecimal.ZERO
            else (bill.price?.multiply(BigDecimal(bill.amount ?: 0)))
        return billMapper.updateByPrimaryKeySelective(bill)
    }

    fun getAllBill(page: Int, size: Int, userId: Int?): PageView<BillResponseInfo> {
        PageHelper.startPage<Any>(page, size)
        return PageView.build(billMapper.selectAll(userId))
    }

    fun getBill(id: Int): BillResponseInfo {
        return billMapper.selectByPrimaryKey(id) ?: error("不存在该订单：$id")
    }

    fun deleteBill(id: Int): Int {
        return billMapper.deleteByPrimaryKey(id)
    }

    /**
     * 统计销售额
     */
    fun countSalesVolume(): MutableMap<String, Any> {
        val day = billMapper.countSalesVolume(LocalTime.MIN.toLocalDateTime(), LocalTime.MAX.toLocalDateTime())
            ?: BigDecimal.ZERO
        val month = billMapper.countSalesVolume(firstDay(), lastDay()) ?: BigDecimal.ZERO
        val nonPayment = billMapper.countNonPayment() ?: BigDecimal.ZERO
        val statistics = purchaseMapper.statistics()
        statistics["accountPaid"] = statistics["accountPaid"] ?: BigDecimal.ZERO
        statistics["arrearage"] = statistics["arrearage"] ?: BigDecimal.ZERO
        statistics.putAll(mapOf("daySell" to day, "monthSell" to month, "nonPayment" to nonPayment))
        return statistics
    }

    /**
     * 统计未收款记录
     */
    fun findDebt(page: Int, size: Int, field: String, order: String): PageView<BillResponseInfo> {
        val startPage = PageHelper.startPage<BillResponseInfo>(page, size)
        startPage.setOrderBy<BillResponseInfo>("$field $order")
        return PageView.build(billMapper.findDebt())
    }

    fun findTodayBill(page: Int, size: Int): PageView<BillResponseInfo> {
        PageHelper.startPage<BillResponseInfo>(page, size)
        return PageView.build(
            billMapper.findBillByCreateTime(
                LocalTime.MIN.toLocalDateTime(),
                LocalTime.MAX.toLocalDateTime()
            )
        )
    }

    fun findCurrentMonthBill(page: Int, size: Int): PageView<BillResponseInfo> {
        PageHelper.startPage<BillResponseInfo>(page, size)
        return PageView.build(billMapper.findBillByCreateTime(firstDay(), lastDay()))
    }

    /**
     * 统计用户的总消费和总欠款
     */
    fun countConsume(userId: Int): MutableMap<String, BigDecimal> {
        return billMapper.countConsume(userId)
    }

    /**
     * 获取用户的账单
     */
    fun findDetailByCreateTime(
        page: Int,
        size: Int,
        userId: Int,
        isDebt: Boolean,
        startTime: LocalDateTime? = null,
        endTime: LocalDateTime? = null
    ): PageView<BillResponseInfo> {
        PageHelper.startPage<BillResponseInfo>(page, size)
        return PageView.build(billMapper.findDetail(userId, if (isDebt) "=" else "!=", startTime, endTime))
    }

    /**
     * 批量更新已付款
     */
    fun batchUpdatePayment(ids: List<Int>, paymentType: String): Int {
        if (paymentType == "wzf") error("不能批量修改为未支付")
        return billMapper.batchUpdatePayment(ids, paymentType)
    }

    fun outExel(startTime: LocalDateTime, endTime: LocalDateTime): String {
        val list = billMapper.findBillByCreateTime(startTime, endTime)
        val workbook = HSSFWorkbook()
        //创建Excel工作表对象
        val sheet = workbook.createSheet("工作表")
        var style = workbook.createCellStyle()
        style.alignment = HorizontalAlignment.CENTER
        val row = sheet.createRow(0)
        val cell1 = row.createCell(0)
        cell1.setCellValue("开单人")
        cell1.setCellStyle(style)
        val cell2 = row.createCell(1)
        cell2.setCellValue("车型")
        cell2.setCellStyle(style)
        val cell3 = row.createCell(2)
        cell3.setCellValue("规格")
        cell3.setCellStyle(style)
        val cell4 = row.createCell(3)
        cell4.setCellValue("数量")
        cell4.setCellStyle(style)
        val cell5 = row.createCell(4)
        cell5.setCellValue("价格")
        cell5.setCellStyle(style)
        val cell6 = row.createCell(5)
        cell6.setCellValue("备注")
        cell6.setCellStyle(style)
        val cell7 = row.createCell(6)
        cell7.setCellValue("日期")
        cell7.setCellStyle(style)
        sheet.setColumnWidth(6, 256 * 15)
        for (i in 1..list.size) {
            val dataRow = sheet.createRow(i)
            val name = dataRow.createCell(0)
            name.setCellValue(list[i - 1].username)
            val type = dataRow.createCell(1)
            type.setCellValue(list[i - 1].type)
            val specification = dataRow.createCell(2)
            specification.setCellValue(list[i - 1].specification)
            val amount = dataRow.createCell(3)
            amount.setCellValue(list[i - 1].amount?.toDouble() ?: 0.0)
            val price = dataRow.createCell(4)
            price.setCellValue(list[i - 1].price?.toPlainString() ?: "")
            val remark = dataRow.createCell(5)
            remark.setCellValue(list[i - 1].remark)
            val time = dataRow.createCell(6)
            time.setCellValue(list[i - 1].createTime)
            style = workbook.createCellStyle()
            style.dataFormat = HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm")
            time.setCellStyle(style)
        }
        val name = SimpleDateFormat("yyyyMMdd-HHmmss").format(Date()).toString() + ".xls"
        val file = File(name)
        if (!file.exists()) file.createNewFile()
        file.outputStream().use {
            workbook.write(it)
        }
        val updateFile = COSClient.updateExel(name, file)
        file.delete()
        return updateFile
    }
}