public class Haversine {

  public static double havDistance(double lat1, double lon1, double lat2, double lon2){
    lat1 = Math.toRadians(lat1);
    lon1 = Math.toRadians(lon1);
    lat2 = Math.toRadians(lat2);
    lon2 = Math.toRadians(lon2);

    double dlon = lon2 - lon1;
    double dlat = lat2 - lat1;

    double a = Math.pow(Math.sin(dlat/2),2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon/2),2);
    double c = 2 * Math.asin(Math.sqrt(a));
    int r = 6371;
    return c * r;
  }
}
