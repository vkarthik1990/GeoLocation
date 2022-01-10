public class Hawker {

    double latitude;
    double longitude;
    private String name;
    private String photourl;
    private String status;
    double distance;

    public Hawker(String hawkername, String cordinates, String photourl, String status) {
        this.latitude= Double.parseDouble(cordinates.split(",")[0]);
        this.name=hawkername;
        this.longitude= Double.parseDouble(cordinates.split(",")[1]);
        this.photourl=photourl;
        this.status=status;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
