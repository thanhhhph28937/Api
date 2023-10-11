package com.example.api;




import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;



public class Chay {
    public static void main(String[] args) {
        try {
            // URL của API NASA
            String apiUrl = "https://images-api.nasa.gov/search?q=clouds";

            // Tạo một kết nối HTTP đến URL
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Thiết lập phương thức yêu cầu thành GET
            connection.setRequestMethod("GET");

            // Đọc dữ liệu JSON từ API
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Chuyển đổi chuỗi JSON thành đối tượng JSON
            JSONObject jsonResponse = new JSONObject(response.toString());

            // Lấy mảng các phần tử từ đối tượng JSON
            JSONArray items = jsonResponse.getJSONObject("collection").getJSONArray("items");
            for (int i = 0; i < 100; i++) {
                JSONObject x = items.getJSONObject(i);
                System.out.println("phan tu thu "+i+":"+x);

            }
            if (items.length() >= 1) {
                JSONObject item = items.getJSONObject(0);

                for (String key : item.keySet()) {
                    System.out.print(key + ": ");
                    Object value = item.get(key);
                    System.out.println(value);

                }
                String jsonArrayString = "[{\"keywords\":[\"Cloud vortices\",\"Heard Island\",\"south Indian Ocean\"],\"media_type\":\"image\",\"date_created\":\"2015-11-02T00:00:00Z\",\"center\":\"GSFC\",\"description\":\"Cloud vortices off Heard Island, south Indian Ocean. The Moderate Resolution Imaging Spectroradiometer (MODIS) aboard NASA's Aqua satellite captured this true-color image of sea ice off Heard Island on Nov 2, 2015 at 5:02 AM EST (09:20 UTC). Credit: NASA/GSFC/Jeff Schmaltz/MODIS Land Rapid Response Team\",\"title\":\"Cloud vortices\",\"nasa_id\":\"cloud-vortices_22531636120_o\",\"description_508\":\"Cloud vortices\"}]";
                String mang="[{\"rel\":\"preview\",\"href\":\"https://images-assets.nasa.gov/image/cloud-vortices_22531636120_o/cloud-vortices_22531636120_o~thumb.jpg\",\"render\":\"image\"}]";

                // Phân tích chuỗi JSON thành mảng JSON
                JSONArray jsonArray = new JSONArray(jsonArrayString);
                JSONArray jsonArray1 = new JSONArray(mang);

                // Lấy giá trị của khóa "keywords" từ phần tử đầu tiên của mảng
                if (jsonArray.length() > 0) {
                    JSONObject firstObject = jsonArray.getJSONObject(0);
                    JSONArray keywordsArray = firstObject.getJSONArray("keywords");
                    for (int i = 0; i < keywordsArray.length(); i++) {
                        String keyword = keywordsArray.getString(i);
                        System.out.println("Keyword " + (i + 1) + ": " + keyword);
                    }
                    JSONObject second=jsonArray.getJSONObject(0);
                    String se = second.getString("media_type");
                    System.out.println("media_type:"+se);
                    JSONObject thi = jsonArray.getJSONObject(0);
                    String third = thi.getString("date_created");
                    System.out.println("date_created:"+third);
                    JSONObject fou = jsonArray.getJSONObject(0);
                    String four = fou.getString("center");
                    System.out.println("center:"+four);
                    JSONObject fiv= jsonArray.getJSONObject(0);
                    String five = fiv.getString("description");
                    System.out.println("description:"+five);
                    JSONObject si= jsonArray.getJSONObject(0);
                    String six = si.getString("title");
                    System.out.println("title:"+six);
                    JSONObject sev= jsonArray.getJSONObject(0);
                    String seven = sev.getString("nasa_id");
                    System.out.println("nasa_id:"+seven);
                    JSONObject eig= jsonArray.getJSONObject(0);
                    String eight = eig.getString("description_508");
                    System.out.println("description_508:"+eight);
                    JSONObject chi= jsonArray1.getJSONObject(0);
                    String chin = chi.getString("rel");
                    System.out.println("rel:"+chin);
                    JSONObject muo= jsonArray1.getJSONObject(0);
                    String muoi = muo.getString("href");
                    System.out.println("href:"+muoi);
                    JSONObject mm= jsonArray1.getJSONObject(0);
                    String mmot = mm.getString("render");
                    System.out.println("render:"+mmot);


                }

                else {
                    System.out.println("Mảng JSON trống.");
                }
//                if (item.has("data")) {
//                    String  data = item.getString("data");
//                    System.out.println("href:"+data);
//
//                }
            } else {
                System.out.println("Không có phần tử trong mảng.");
            }


            // Đóng kết nối
            connection.disconnect();
        } catch (IOException e) {
            System.err.println("Loi khi thuc hien yeu cau HTTP: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Loi xu ly du lieu JSON: " + e.getMessage());
        }
    }
}
