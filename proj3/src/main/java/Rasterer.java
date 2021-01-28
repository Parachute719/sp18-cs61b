import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    private static final double[] LONDPP_AT_DEPTH = {
            (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / (MapServer.TILE_SIZE * numTilesAtDepth(0)),
            (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / (MapServer.TILE_SIZE * numTilesAtDepth(1)),
            (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / (MapServer.TILE_SIZE * numTilesAtDepth(2)),
            (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / (MapServer.TILE_SIZE * numTilesAtDepth(3)),
            (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / (MapServer.TILE_SIZE * numTilesAtDepth(4)),
            (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / (MapServer.TILE_SIZE * numTilesAtDepth(5)),
            (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / (MapServer.TILE_SIZE * numTilesAtDepth(6)),
            (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / (MapServer.TILE_SIZE * numTilesAtDepth(7)),
    };

    private static int numTilesAtDepth(int depth) {
        return (int) Math.pow(2, depth);
    }

    public Rasterer() {
        // YOUR CODE HERE
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        System.out.println(params);
        Map<String, Object> results = new HashMap<>();

        double lrlon = params.get("lrlon");
        double ullon = params.get("ullon");
        double lrlat = params.get("lrlat");
        double ullat = params.get("ullat");
        double width = params.get("w");

        double queryBoxLonDPP = lonDPP(ullon, lrlon, width);
        int depth = selectDepth(queryBoxLonDPP);
        boolean success = areValidCoordinates(ullon, ullat, lrlon, lrlat);

        if (!success) {
            results.put("query_success", success);
            results.put("depth", depth);
            results.put("render_grid", null);
            results.put("raster_ul_lon", 0);
            results.put("raster_ul_lat", 0);
            results.put("raster_lr_lon", 0);
            results.put("raster_lr_lat", 0);
        } else {
            String[][] renderGrid = selectRenderGrid(ullon, ullat, lrlon, lrlat, depth);
            results.put("query_success", success);
            results.put("depth", depth);
            results.put("render_grid", renderGrid);
            results.put("raster_ul_lon", ullonConverter(ullon, depth));
            results.put("raster_ul_lat", ullatConverter(ullat, depth));
            results.put("raster_lr_lon", lrlonConverter(lrlon, depth));
            results.put("raster_lr_lat", lrlatConverter(lrlat, depth));
        }

        //System.out.println("Since you haven't implemented getMapRaster, nothing is displayed in "
                           //+ "your browser.");
        return results;
    }

    private double lonDPP(double lon1, double lon2, double imageWidth) {
        return Math.abs(lon1 - lon2) / imageWidth;
    }

    private int selectDepth(double LonDPP) {
        for (int i = 0; i < 8; i++) {
            if (LonDPP >= LONDPP_AT_DEPTH[i]) {
                return i;
            }
        }
        return 7;
    }

    private boolean areValidCoordinates(double ullon, double ullat, double lrlon, double lrlat) {
        return (ullon < lrlon && ullat > lrlat)
                && (ullon < MapServer.ROOT_LRLON && lrlon > MapServer.ROOT_ULLON)
                && (ullat > MapServer.ROOT_LRLAT && lrlat < MapServer.ROOT_ULLAT);
    }

    private String[][] selectRenderGrid(double ullon, double ullat, double lrlon, double lrlat, int depth) {
        int xStart = xStartConverter(ullon, depth);
        int xEnd = xEndConverter(lrlon, depth);
        int yStart = yStartConverter(ullat, depth);
        int yEnd = yEndConverter(lrlat, depth);
        int xNum = xEnd - xStart + 1;
        int yNum = yEnd - yStart + 1;
        String[][] renderGrid = new String[yNum][xNum];
        for (int i = xStart; i <= xEnd; i++) {
            for (int j = yStart; j <= yEnd; j++) {
                renderGrid[j - yStart][i - xStart] = "d" + depth + "_x" + i + "_y" + j + ".png";
                System.out.println(renderGrid[j - yStart][i - xStart]);
            }
        }
        return renderGrid;

    }

    private double ullonConverter(double lon, int depth) {
        double lonUnit = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / numTilesAtDepth(depth);
        int x = xStartConverter(lon, depth);
        return (MapServer.ROOT_ULLON + lonUnit * x);
    }

    private double lrlonConverter(double lon, int depth) {
        double lonUnit = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / numTilesAtDepth(depth);
        int x = xEndConverter(lon, depth) + 1;
        return (MapServer.ROOT_ULLON + lonUnit * x);
    }

    private double ullatConverter(double lat, int depth) {
        double latUnit = (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / numTilesAtDepth(depth);
        int y = yStartConverter(lat, depth);
        return (MapServer.ROOT_ULLAT - latUnit * y);
    }

    private double lrlatConverter(double lat, int depth) {
        double latUnit = (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / numTilesAtDepth(depth);
        int y = yEndConverter(lat, depth) + 1;
        return (MapServer.ROOT_ULLAT - latUnit * y);
    }

    private int xStartConverter(double ullon, int depth) {
        double lonUnit = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / numTilesAtDepth(depth);
        if (ullon <= MapServer.ROOT_ULLON) {
            return 0;
        } else {
            return (int) ((ullon - MapServer.ROOT_ULLON) / lonUnit);
        }
    }

    private int xEndConverter(double lrlon, int depth) {
        double lonUnit = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / numTilesAtDepth(depth);
        if (lrlon >= MapServer.ROOT_LRLON) {
            return (numTilesAtDepth(depth) - 1);
        } else {
            return (int) ((lrlon - MapServer.ROOT_ULLON) / lonUnit);
        }
    }

    private int yStartConverter(double ullat, int depth) {
        double latUnit = (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / numTilesAtDepth(depth);
        if (ullat >= MapServer.ROOT_ULLAT) {
            return 0;
        } else {
            return (int) ((MapServer.ROOT_ULLAT - ullat) / latUnit);
        }
    }

    private int yEndConverter(double lrlat, int depth) {
        double latUnit = (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / numTilesAtDepth(depth);
        if (lrlat <= MapServer.ROOT_LRLAT) {
            return (numTilesAtDepth(depth) - 1);
        } else {
            return (int) ((MapServer.ROOT_ULLAT - lrlat) / latUnit);
        }
    }


}
