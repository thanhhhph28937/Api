package com.example.api;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@WebServlet("/thuongdangcap")
public class Api extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/result.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

            if (items.length() >= 1) {
                // Lấy phần tử thứ 1
                JSONObject item = items.getJSONObject(0);

                for (String key : item.keySet()) {
                    System.out.print(key + ": ");
                    Object value = item.get(key);
                    System.out.println(value);

                }
                String jsonArrayString = "[{\"keywords\":[\"Cloud vortices\",\"Heard Island\",\"south Indian Ocean\"],\"media_type\":\"image\",\"date_created\":\"2015-11-02T00:00:00Z\",\"center\":\"GSFC\",\"description\":\"Cloud vortices off Heard Island, south Indian Ocean. The Moderate Resolution Imaging Spectroradiometer (MODIS) aboard NASA's Aqua satellite captured this true-color image of sea ice off Heard Island on Nov 2, 2015 at 5:02 AM EST (09:20 UTC). Credit: NASA/GSFC/Jeff Schmaltz/MODIS Land Rapid Response Team\",\"title\":\"Cloud vortices\",\"nasa_id\":\"cloud-vortices_22531636120_o\",\"description_508\":\"Cloud vortices\"}]";

                // Phân tích chuỗi JSON thành mảng JSON
                JSONArray jsonArray = new JSONArray(jsonArrayString);

                // Lấy giá trị của khóa "keywords" từ phần tử đầu tiên của mảng
                if (jsonArray.length() > 0) {
                    JSONObject firstObject = jsonArray.getJSONObject(0);
                    JSONArray keywordsArray = firstObject.getJSONArray("keywords");
                    JSONObject second=jsonArray.getJSONObject(0);
                    String se = second.getString("media_type");
                    System.out.println(se);

                    // Lấy danh sách từ khóa
                    for (int i = 0; i < keywordsArray.length(); i++) {
                        String keyword = keywordsArray.getString(0);
                        System.out.println("Keyword " + ": " + keyword);
                        req.setAttribute("keyword0",keyword);
                        req.getRequestDispatcher("result.jsp").forward(req,resp);
                    }
                } else {
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
