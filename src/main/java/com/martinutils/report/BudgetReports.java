package com.martinutils.report;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class BudgetReports
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");

            // First, load JasperDesign from XML and compile it into
            // JasperReport
            JasperDesign jasperDesign = JRXmlLoader.load("AccountBudget.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            // Second, create a map of parameters to pass to the report.
            Map parameters = new HashMap();
            parameters.put("YEAR", args[0]);
            // Third, get a database connection

            Connection conn = DriverManager.getConnection("jdbc:mysql://myth-desktop/cash",
                    args[1],
                    args[2]);
            // Fourth, create JasperPrint using fillReport() method
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
                    parameters,
                    conn);
            // You can use JasperPrint to create PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint,
                    "BudgetReport" + args[0] + ".pdf");
            // Or to view report in the JasperViewer
            // JasperViewer.viewReport(jasperPrint);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
