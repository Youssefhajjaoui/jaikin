import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class application extends JPanel {
    private final List<Point> controlPoints = new ArrayList<>();
    private final List<List<Point>> steps = new ArrayList<>();
    private int currentStep = 0;
    private boolean animating;
    private Timer timer;
    private Point selectedPoint = null;
    private int dragThreshold = 10;
    private boolean err;

    public application() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.black);

        // Mouse listener to add points
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {

                    for (Point p : controlPoints) {
                        if (p.distance(e.getPoint()) <= dragThreshold) {
                            selectedPoint = p;
                            return;
                        }
                    }
                    if (!animating) {

                        controlPoints.add(new Point(e.getX(), e.getY()));
                        repaint();
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                selectedPoint = null;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (selectedPoint != null) {
                    selectedPoint.setLocation(e.getX(), e.getY());
                    repaint();
                }
            }
        });
        // Key listener for Enter and Escape
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (controlPoints.size() >= 2) {
                        err = false;
                        steps.clear();
                        steps.addAll(algo.generateChaikinSteps(controlPoints));
                        animating = true;

                        startAnimation();
                    } else {
                        err = true;
                        repaint();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    controlPoints.clear();
                    steps.clear();
                    currentStep = 0;
                    animating = false;
                    err = false;

                    if (timer != null) {
                        timer.stop();
                        timer = null;
                    }
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (err) {
            g.setColor(Color.RED);
            g.drawString("You need to select more Points.", 20, 20);
        }
        if (controlPoints.isEmpty())
            return;

        g2.setColor(Color.RED);
        for (Point p : controlPoints) {
            drawPoint(g2, p);
        }

        g2.setStroke(new BasicStroke(2));

        if (controlPoints.size() == 1) {
            drawPoint(g2, controlPoints.get(0));
        } else {
            List<Point> toDraw = (steps.isEmpty() ? controlPoints : steps.get(currentStep));
            g2.setColor(Color.WHITE);
            if (animating) {

                for (int i = 0; i < toDraw.size() - 1; i++) {
                    Point p1 = toDraw.get(i);
                    Point p2 = toDraw.get(i + 1);
                    g2.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            }

        }
    }

    private void startAnimation() {

        if (timer != null)
            timer.stop();
        currentStep = 0;
        repaint();

        timer = new Timer(1000, e -> {
            steps.clear();
            steps.addAll(algo.generateChaikinSteps(controlPoints));
            currentStep = (currentStep + 1) % steps.size();
            repaint();
        });
        timer.start();
    }

    private void drawPoint(Graphics2D g2, Point p) {
        g2.fillOval(p.x - 4, p.y - 4, 8, 8);
    }

    public List<Point> getControlPoints() {
        return this.controlPoints;
    }

    public List<List<Point>> getSteps() {
        return this.steps;
    }

    public int GetcurrentStep() {
        return this.currentStep;
    }

    public void setCurretStep(int step) {
        this.currentStep = step;
    }

    public Timer getTimer() {
        return this.timer;
    }
}
