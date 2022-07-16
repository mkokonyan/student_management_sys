package enums;

public enum TeacherDegree {
    MSC("MSc"),
    BSC("BSc"),
    PHD("PHD");

    private final String teacherDegree;

    TeacherDegree(String teacherDegree) {
        this.teacherDegree = teacherDegree;
    }

    public String getTeacherDegree() {
        return this.teacherDegree;
    }

}
