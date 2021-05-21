package com.example.vqhcovid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InformationRssActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> arrayList, arrLink;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_rss);
        // https://vnexpress.net/rss/suc-khoe.rss, https://vietnamnet.vn/rss/suc-khoe.rss ,
        // https://ncov.moh.gov.vn/vi/web/guest/rss/-/journal/rss/20182/7199277
        //https://vtc.vn/rss/suc-khoe.rss
        new ReadRss().execute("https://vtc.vn/rss/suc-khoe.rss");
//        android.R.layout.simple_list_item_1
        listView = (ListView) findViewById(R.id.lvtitle);
        arrayList = new ArrayList<>();
        arrLink = new ArrayList<>();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(InformationRssActivity.this, WebviewActivity.class);
                intent.putExtra("link tin tuc", arrLink.get(i));
                startActivity(intent);
            }
        });

    }

    // ddocj tep rss
    private class ReadRss extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line);
                }
                bufferedReader.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);

            XMLDOMParser parser = new XMLDOMParser();

            Document document = parser.getDocument(s);
            NodeList nodeList = document.getElementsByTagName("item");


            String tieude = "";

            // doc list
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                tieude = parser.getValue(element, "title");
                arrayList.add(tieude);
                arrLink.add(parser.getValue(element, "link"));

                String a = parser.getValue(element, "description");
                String x =tachimg(a);
                Toast.makeText(InformationRssActivity.this,x,Toast.LENGTH_LONG).show();


            }
            adapter.notifyDataSetChanged();


        }
    }

    private String tachimg(String a) {
        List<String> pics = new ArrayList<String>();
        String img = "";
        Pattern p_image;
        Matcher m_image;
        // String regEx_img = "<img.*src=(.*?)[^>]*?>"; //Image link address
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile
                (regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(a);
        while (m_image.find()) {
            // get <img /> data
            img = m_image.group();
            // match the src data in <img>
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                pics.add(m.group(1));
            }
        }

        return String.valueOf(pics);
    }
}