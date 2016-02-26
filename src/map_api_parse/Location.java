
package map_api_parse;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Location {

    @Expose
    private Float lat;
    @Expose
    private Float lng;

    /**
     * 
     * @return
     *     The lat
     */
    public Float getLat() {
        return lat;
    }

    /**
     * 
     * @param lat
     *     The lat
     */
    public void setLat(Float lat) {
        this.lat = lat;
    }

    /**
     * 
     * @return
     *     The lng
     */
    public Float getLng() {
        return lng;
    }

    /**
     * 
     * @param lng
     *     The lng
     */
    public void setLng(Float lng) {
        this.lng = lng;
    }

}
