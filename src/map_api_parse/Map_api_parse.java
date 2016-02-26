/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map_api_parse;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author sajjad
 */
public class Map_api_parse {

    /**
     * @param args the command line arguments
     */
    static boolean test = true;
    static boolean ad_level_1 = true, ad_level_2 = true, ad_level_3 = true, country = true;
    static String place_ad_level_1 = null, place_ad_level_2 = null, place_ad_level_3 = null, place_country = null;

    public static void main(String[] args) throws IOException {
        // TODO code application logic here

        FileWriter fw = null;
        BufferedWriter bw = null;

        File LIS_dir = new File("DATA");
        File[] LIS_files = LIS_dir.listFiles();

        for (File f : LIS_files) {
            if (f.isFile()) {
                //System.out.println("file  " + j + " ");
                BufferedReader inputStream = null;
                try {
                    inputStream = new BufferedReader(new FileReader(f));
                    String line;

                    while ((line = inputStream.readLine()) != null) {
                        String[] lineSplit = line.split("CRACKER");
                        String extraInfo = lineSplit[0];

                        String[] extraInfoSplit = extraInfo.split("\\s+");
                        String date_extraInfo = extraInfoSplit[2];
                        String lat_extraInfo = extraInfoSplit[4];
                        String lng_extraInfo = extraInfoSplit[6];
                        String jaonResponse = lineSplit[1];
                        Gson gson2 = new Gson();
                        try {
                            gson2.fromJson(jaonResponse.trim(), GoogleMapApiLatLngToUpazila.class);
                            GoogleMapApiLatLngToUpazila googleMapApiLatLngToUpazila = gson2.fromJson(jaonResponse.trim(), GoogleMapApiLatLngToUpazila.class);

                            if (googleMapApiLatLngToUpazila.getStatus().equals("OK")) {
                                String temp = "", place_formatted_address = "";
                                for (int i = 0; i < googleMapApiLatLngToUpazila.getResults().size(); i++) {

                                    for (int j = 0; j < googleMapApiLatLngToUpazila.getResults().get(i).getAddressComponents().size(); j++) {

                                        if (ad_level_1 == true && googleMapApiLatLngToUpazila.getResults().get(i).getAddressComponents().get(j).getTypes().get(0).equals("administrative_area_level_1")) {
                                            place_ad_level_1 = googleMapApiLatLngToUpazila.getResults().get(i).getAddressComponents().get(j).getLongName();
                                            ad_level_1 = false;
                                        }
                                        if (ad_level_2 == true && googleMapApiLatLngToUpazila.getResults().get(i).getAddressComponents().get(j).getTypes().get(0).equals("administrative_area_level_2")) {
                                            place_ad_level_2 = googleMapApiLatLngToUpazila.getResults().get(i).getAddressComponents().get(j).getLongName();
                                            ad_level_2 = false;
                                        }
                                        if (ad_level_3 == true && googleMapApiLatLngToUpazila.getResults().get(i).getAddressComponents().get(j).getTypes().get(0).equals("administrative_area_level_3")) {
                                            place_ad_level_3 = googleMapApiLatLngToUpazila.getResults().get(i).getAddressComponents().get(j).getLongName();
                                            ad_level_3 = false;
                                        }
                                        if (country == true && googleMapApiLatLngToUpazila.getResults().get(i).getAddressComponents().get(j).getTypes().get(0).equals("country")) {
                                            place_country = googleMapApiLatLngToUpazila.getResults().get(i).getAddressComponents().get(j).getLongName();
                                            country = false;
                                        }
                                    }
                                    temp = googleMapApiLatLngToUpazila.getResults().get(i).getFormattedAddress();
                                    if (temp.length() > place_formatted_address.length()) {
                                        place_formatted_address = temp;
                                    }
                                }
                                if (ad_level_3 == true) {
                                    System.out.println(" dfsdfs" + place_ad_level_3);
                                }
                                //System.out.println("getFormattedAddress " + place_formatted_address);
                                String palce_NE_lat = "" + googleMapApiLatLngToUpazila.getResults().get(0).getGeometry().getBounds().getNortheast().getLat();
                                String palce_NE_lng = "" + googleMapApiLatLngToUpazila.getResults().get(0).getGeometry().getBounds().getNortheast().getLng();
                                String place_SW_lat = "" + googleMapApiLatLngToUpazila.getResults().get(0).getGeometry().getBounds().getSouthwest().getLat();
                                String place_SW_lng = "" + googleMapApiLatLngToUpazila.getResults().get(0).getGeometry().getBounds().getSouthwest().getLng();
                                String status = "OK";
                                System.out.println(" ..................................");

                                File file = new File("DATA\\parsed_map_api_data.txt");
                                if (!file.exists()) {
                                    file.createNewFile();
                                }
                                fw = new FileWriter(file, true);
                                bw = new BufferedWriter(fw);

                                fw.append("status" + " " + status + " "
                                        + "country" + " " + place_country + " "
                                        + "date" + " " + date_extraInfo + " "
                                        + "lat" + " " + lat_extraInfo + " "
                                        + "lng" + " " + lng_extraInfo + " "
                                        + "palce_NE_lat" + " " + palce_NE_lat + " "
                                        + "palce_NE_lng" + " " + palce_NE_lng + " "
                                        + "place_SW_lat" + " " + place_SW_lat + " "
                                        + "place_SW_lng" + " " + place_SW_lng + " "
                                        + "place_ad_level_1" + " " + place_ad_level_1 + " "
                                        + "place_ad_level_2" + " " + place_ad_level_2 + " "
                                        + "place_ad_level_3" + " " + place_ad_level_3 + " "
                                        + "place_formatted_address" + " " + place_formatted_address + " "
                                        + "SPLITER"
                                        +"\n"
                                );
                            } else {
                                fw.append("status" + " " + "null" + " "
                                        + "country" + " " + "null" + " "
                                        + "date" + " " + "null" + " "
                                        + "lat" + " " + "null" + " "
                                        + "lng" + " " + "null" + " "
                                        + "palce_NE_lat" + " " + "null" + " "
                                        + "palce_NE_lng" + " " + "null" + " "
                                        + "place_SW_lat" + " " + "null" + " "
                                        + "place_SW_lng" + " " + "null" + " "
                                        + "place_ad_level_1" + " " + "null" + " "
                                        + "place_ad_level_2" + " " + "null" + " "
                                        + "place_ad_level_3" + " " + "null" + " "
                                        + "place_formatted_address" + " " + "null" + " "
                                        + "SPLITER"
                                        +"\n");
                            }
                            ad_level_1 = true;
                            ad_level_2 = true;
                            ad_level_3 = true;
                            country = true;
                            place_ad_level_1 = null;
                            place_ad_level_2 = null;
                            place_ad_level_3 = null;
                            place_country = null;//place_formatted_address=null;
                            System.out.println(" ");
                            bw.close();
                        } finally {
                            continue;
                        }
                    }
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                }
            }
        }
        //bw.close();
    }
}
