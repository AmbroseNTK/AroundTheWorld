package ntk.ambrose.aroundtheworld.Models;

import android.content.Context;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class WorldMap {
    public final int WIDTH = 12;
    public final int HEIGHT = 6;
    private CountryUnit[][] map;
    private HashMap<String,String> codeTable;
    private WorldMap() {
        map = new CountryUnit[HEIGHT][WIDTH];
        codeTable=new HashMap<>();
        for(int i=0;i<HEIGHT;i++){
            for(int j=0;j<WIDTH;j++){
                map[i][j]=new CountryUnit();
            }
        }
    }
    private static WorldMap instance;
    public static WorldMap getInstance(){
        if(instance==null)
            instance=new WorldMap();
        return instance;
    }
    public void createWorldMap(Context context){
        try {
            InputStream inputStream = context.getAssets().open("world.xml");
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);
            Element element =document.getDocumentElement();
            element.normalize();
            NodeList nodeList = document.getElementsByTagName("country");
            Node node;
            for(int i=0;i<nodeList.getLength();i++){
                node = nodeList.item(i);
                if(node.getNodeType()==Node.ELEMENT_NODE){
                    Element subElement = (Element)node;
                    Country country = new Country(getNodeValue("name",subElement),getNodeValue("code",subElement),getNodeValue("lang",subElement));
                    int x = Integer.parseInt(getNodeValue("x",subElement));
                    int y = Integer.parseInt(getNodeValue("y",subElement));
                    codeTable.put(country.getCode(),country.getName());
                    map[y-1][x-1].getUnit().add(country);
                }
            }
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static String getNodeValue(String tag, Element element) {
        try {
            NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
            Node node = nodeList.item(0);
            return node.getNodeValue();
        }catch(Exception e){
            Log.i("APP",e.getMessage());
            return "";
        }
    }

    public CountryUnit[][] getMap() {
        return map;
    }
    public CountryUnit getCell(int height, int width){
        return map[height][width];
    }
    public String codeToName(String code){
        return codeTable.containsKey(code)?codeTable.get(code):"";
    }
}
