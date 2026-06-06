package tw.edu.fju.miniclinic.model;

public class AppointmentForm {
    private String chartNo;       // 病歷號
    private String doctorId;      // 掛號的醫師
    private String apptDate;      // 日期
    private String timeSlot;      // 時段

    public AppointmentForm() {}

    public String getChartNo() { return chartNo; }
    public void setChartNo(String chartNo) { this.chartNo = chartNo; }
    public String getDoctorId() { return doctorId; }
    public void setDoctorId(String doctorId) { this.doctorId = doctorId; }
    public String getApptDate() { return apptDate; }
    public void setApptDate(String apptDate) { this.apptDate = apptDate; }
    public String getTimeSlot() { return timeSlot; }
    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }
}