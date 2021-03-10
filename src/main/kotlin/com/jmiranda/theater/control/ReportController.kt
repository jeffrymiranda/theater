package com.jmiranda.theater.control;

import com.jmiranda.theater.service.ReportingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView
import javax.websocket.server.PathParam
import kotlin.reflect.full.declaredMemberFunctions

@Controller
@RequestMapping("reports")
class ReportController(
    val reportingService: ReportingService
) {
    private fun getReportList() = this.reportingService::class.declaredMemberFunctions.map { it.name }

    private fun toTitleCase(reportFunction: String): Report {
        val name = reportFunction
            .split("_")
            .joinToString(
                " ",
                transform = String::capitalize
            )
        return Report(reportFunction, name)
    }

    @RequestMapping
    fun main(): ModelAndView {
        val reports = ArrayList<Report>()
        this.getReportList().forEach { reports.add(toTitleCase(it)) }
        return ModelAndView("reports", mapOf("reports" to reports))
        //return ModelAndView("reports", mapOf("reports" to this.getReportList()))
    }

    @RequestMapping(value = ["getReport"])
    fun getReport(@PathParam("report") report: String): ModelAndView {
        val matchedReport =
            this.reportingService::class.declaredMemberFunctions.firstOrNull { it.name == report }
        val result = matchedReport?.call(this.reportingService) ?: ""

        val reports = ArrayList<Report>()
        this.getReportList().forEach { reports.add(toTitleCase(it)) }
        return ModelAndView("reports", mapOf("reports" to reports, "result" to result))
        //return ModelAndView("reports", mapOf("reports" to this.getReportList(), "result" to result))
    }

    class Report(val function: String, val name: String)
}


