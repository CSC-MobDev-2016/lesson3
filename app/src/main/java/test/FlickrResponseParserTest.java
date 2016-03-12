package test;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.csc.lesson3.apis.flickr_image_search.FlickrResponseParser;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class FlickrResponseParserTest extends ApplicationTestCase<Application> {
     private static final String TYPICAL_RESPONSE =
             "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
             "<rsp stat=\"ok\">\n" +
             "<photos page=\"1\" pages=\"104022\" perpage=\"3\" total=\"312066\">\n" +
             "\t<photo id=\"25720932205\" owner=\"53585902@N06\" secret=\"dde3ce4930\" server=\"1597\" farm=\"2\" title=\"ðŸ\u008DŽ\" ispublic=\"1\" isfriend=\"0\" isfamily=\"0\" />\n" +
             "\t<photo id=\"25694662556\" owner=\"137872055@N03\" secret=\"a4f9e40693\" server=\"1489\" farm=\"2\" title=\"Apple Car con problemas para desarrollar su coche elÃ©ctrico\" ispublic=\"1\" isfriend=\"0\" isfamily=\"0\" />\n" +
             "\t<photo id=\"25419644100\" owner=\"45915993@N00\" secret=\"4a8110ea38\" server=\"1638\" farm=\"2\" title=\"OLYMPUS PEN-F and Pana-LEICA Vario-Elmar 100-400mm f4-6.3\" ispublic=\"1\" isfriend=\"0\" isfamily=\"0\" />\n" +
             "</photos>\n" +
             "</rsp>";

     public FlickrResponseParserTest() {
          super(Application.class);
     }

     @Test
     public void testTypicalResponseParsing() throws Exception {
         List<String> urls = new FlickrResponseParser().parse(TYPICAL_RESPONSE);
         Assert.assertEquals(3, urls.size());
         Assert.assertEquals("https://farm2.staticflickr.com/1597/25720932205_dde3ce4930.jpg", urls.get(0));
         Assert.assertEquals("https://farm2.staticflickr.com/1489/25694662556_a4f9e40693.jpg", urls.get(1));
         Assert.assertEquals("https://farm2.staticflickr.com/1638/25419644100_4a8110ea38.jpg", urls.get(2));
     }

}