package com.shankeerthan;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Zoom {

    static Image image1;
    static int angel;


    // public static void main(String[] args) {
    //    image1 = new Image("file:" + "NewIcons/main.png");
    //  startZoom(image1);
    // }


    public static void saveImage(Image image) {
        image1 = image;

    }

    public static void startZoom() {
        Stage stage = new Stage();

        Group group = new Group();


        // create canvas
        PannableCanvas canvas = new PannableCanvas();

        // we don't want the canvas on the top/left in this example => just
        // translate it a bit
        //  canvas.setTranslateX(500);
        // canvas.setTranslateY(100);

        // create sample nodes which can be dragged
        // NodeGestures nodeGestures = new NodeGestures(canvas);

        ImageView imageView = new ImageView(image1);
        //imageView.setRotate(90);
        //imageView.setFitWidth(700);
        // imageView.setFitHeight(500);
        //   ScrollBar scrollBar = new ScrollBar();
        ScrollPane scrollPane = new ScrollPane(canvas);
        //  sc.setFitToHeight(true);
        // Set content for ScrollPane
        scrollPane.setContent(canvas);

        // Always show vertical scroll bar
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        // Horizontal scroll bar is only displayed when needed
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setContent(canvas);
        // Pannable.
        // scrollPane.setPannable(true);
        Button zoomSelectionButton = new Button();
        Image zoomSelectionIcon = new Image("file:" + "NewIcons/rot.png");
        zoomSelectionButton.setGraphic(new ImageView(zoomSelectionIcon));

        canvas.getChildren().addAll(imageView, zoomSelectionButton);
        //canvas.setRotate(90);

        group.getChildren().addAll(canvas);

        // create scene which can be dragged and zoomed
        Scene scene = new Scene(scrollPane);

        SceneGestures sceneGestures = new SceneGestures(canvas);
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
        scene.addEventFilter(MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
        scene.addEventFilter(ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());

        stage.setScene(scene);
        stage.show();

        canvas.addGrid();
        zoomSelectionButton.setOnMouseClicked(event -> {
            if (angel == 360)
                angel = 0;
            canvas.setRotate(angel);
            angel += 90;
        });

    }


}

class DragContext {

    double mouseAnchorX;
    double mouseAnchorY;

    double translateAnchorX;
    double translateAnchorY;

}

class SceneGestures {

    private static final double MAX_SCALE = 10.0d;
    private static final double MIN_SCALE = .1d;
    PannableCanvas canvas;
    private DragContext sceneDragContext = new DragContext();
    private EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

        public void handle(MouseEvent event) {

            // right mouse button => panning
            if (!event.isPrimaryButtonDown())
                return;

            sceneDragContext.mouseAnchorX = event.getSceneX();
            sceneDragContext.mouseAnchorY = event.getSceneY();

            sceneDragContext.translateAnchorX = canvas.getTranslateX();
            sceneDragContext.translateAnchorY = canvas.getTranslateY();

        }

    };
    private EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent event) {

            // right mouse button => panning
            if (!event.isPrimaryButtonDown())
                return;

            canvas.setTranslateX(sceneDragContext.translateAnchorX + event.getSceneX() - sceneDragContext.mouseAnchorX);
            canvas.setTranslateY(sceneDragContext.translateAnchorY + event.getSceneY() - sceneDragContext.mouseAnchorY);

            event.consume();
        }
    };
    /**
     * Mouse wheel handler: zoom to pivot point
     */
    private EventHandler<ScrollEvent> onScrollEventHandler = new EventHandler<ScrollEvent>() {

        @Override
        public void handle(ScrollEvent event) {

            double delta = 1.2;

            double scale = canvas.getScale(); // currently we only use Y, same value is used for X
            double oldScale = scale;

            if (event.getDeltaY() < 0)
                scale /= delta;
            else
                scale *= delta;

            scale = clamp(scale, MIN_SCALE, MAX_SCALE);

            double f = (scale / oldScale) - 1;

            double dx = (event.getSceneX() - (canvas.getBoundsInParent().getWidth() / 2 + canvas.getBoundsInParent().getMinX()));
            double dy = (event.getSceneY() - (canvas.getBoundsInParent().getHeight() / 2 + canvas.getBoundsInParent().getMinY()));

            canvas.setScale(scale);

            // note: pivot value must be untransformed, i. e. without scaling
            canvas.setPivot(f * dx, f * dy);

            event.consume();

        }

    };

    public SceneGestures(PannableCanvas canvas) {
        this.canvas = canvas;
    }

    public static double clamp(double value, double min, double max) {

        if (Double.compare(value, min) < 0)
            return min;

        if (Double.compare(value, max) > 0)
            return max;

        return value;
    }

    public EventHandler<MouseEvent> getOnMousePressedEventHandler() {
        return onMousePressedEventHandler;
    }

    public EventHandler<MouseEvent> getOnMouseDraggedEventHandler() {
        return onMouseDraggedEventHandler;
    }

    public EventHandler<ScrollEvent> getOnScrollEventHandler() {
        return onScrollEventHandler;
    }
}

