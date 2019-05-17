package round_imagview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

public class round_image extends ImageView {
    private float radius = 18.0f;
    private Path path;
    private RectF rect;
    public round_image(Context context) {
        super(context);
        init();
    }

    private void init() {
        path = new Path();
    }

    public round_image(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public round_image(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public round_image(Context context,  AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        path.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}
