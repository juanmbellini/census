package ar.edu.itba.pod.census.models;

/**
 * Class representing the data to be processed by the system (i.e an habitant from the census).
 */
public class Habitant {

    /**
     * The activity condition id.
     */
    private final int activityConditionId;

    /**
     * The home id.
     */
    private final long homeId;

    /**
     * The department name where the habitant lives.
     */
    private final String departmentName;

    /**
     * The province name where the habitant lives.
     */
    private final Province province;


    /**
     * Constructor.
     *
     * @param activityConditionId The activity condition id.
     * @param homeId              The habitant's home id.
     * @param departmentName      The department name where the habitant is.
     * @param provinceName        The province name where the habitant lives.
     */
    public Habitant(int activityConditionId, long homeId, String departmentName, String provinceName) {
        this.activityConditionId = activityConditionId;
        this.homeId = homeId;
        this.departmentName = departmentName;
        this.province = Province.fromName(provinceName);
    }

    /**
     * @return The activity condition id.
     */
    public int getActivityConditionId() {
        return activityConditionId;
    }

    /**
     * @return The habitant's home id.
     */
    public long getHomeId() {
        return homeId;
    }

    /**
     * @return The department name where the habitant is.
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * @return The province where the habitant lives.
     */
    public Province getProvince() {
        return province;
    }

    /**
     * @return The region where the habitant lives.
     */
    public Region getRegion() {
        return province.getRegion();
    }
}
