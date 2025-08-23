import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class algo {

    public static List<List<Point>> generateChaikinSteps(List<Point> controlPoints) {
        List<List<Point>> steps = new ArrayList<>();
        List<Point> current = new ArrayList<>(controlPoints);
        steps.add(new ArrayList<>(current));

        for (int step = 0; step < 7; step++) {
            List<Point> next = new ArrayList<>();
            next.add(current.get(0)); // keep first point
            for (int i = 0; i < current.size() - 1; i++) {
                Point p0 = current.get(i);
                Point p1 = current.get(i + 1);

                Point Q = new Point((int) (0.75 * p0.x + 0.25 * p1.x), (int) (0.75 * p0.y + 0.25 * p1.y));
                Point R = new Point((int) (0.25 * p0.x + 0.75 * p1.x), (int) (0.25 * p0.y + 0.75 * p1.y));

                next.add(Q);
                next.add(R);
            }
            next.add(current.get(current.size() - 1)); // keep last point
            current = next;
            steps.add(new ArrayList<>(current));
        }
        return steps;
    }
}
