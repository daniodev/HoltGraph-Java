import it.mazambo.typesofedge.Node;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class GraphGraphics extends JFrame {

    List<Node> nodes;
    List<Edge> edges;
    HashMap<Short, Integer> nodeLocation;
    public GraphGraphics(List<Node> nodes, List<Edge> edges){

        this.nodes = nodes;
        this.edges = edges;
        nodeLocation = new HashMap<>();

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int processX = 100;
        int processY = 100;

        int resourceX = 100;
        int resourceY = 250;

        Graphics2D g2d = (Graphics2D) g;

        for (Node node : this.nodes) {

            if(node.isAProcess) {

                g.setColor(Color.YELLOW);
                g.fillOval(processX, processY, 50, 50);

                g.setColor(Color.BLACK);
                g.drawString(String.valueOf(node.getId()), processX, processY);

                nodeLocation.put(node.getId(), processX);
                processX += 70;

            }

            if(!node.isAProcess){

                g.setColor(Color.GREEN);
                g.fillRect(resourceX, resourceY, 50, 50);

                g.setColor(Color.BLACK);
                g.drawString(String.valueOf(node.getId()), resourceX, resourceY - 5);

                nodeLocation.put(node.getId(), resourceX);
                resourceX += 70;

            }

        }

        g2d.setStroke(new BasicStroke(3));

        for(Edge e: this.edges){

            if(!e.getSourceEdge().isAProcess){

                short resourceId = e.getSourceEdge().getId();
                short processId = e.getDestinationEdge().getId();

                int edgeResourceX = nodeLocation.get(resourceId);
                int edgeProcessX = nodeLocation.get(processId);

                g.setColor(Color.GREEN);
                // +25 to make the arrow start in the middle, + 50 to make the arrow start from the bottom of the process
                g.drawLine(edgeResourceX + 25, resourceY, edgeProcessX + 25, processY + 50);
                //Draw the arrow, + 50 to make the triangle point to the process and not be inside
                this.drawArrowHead(g2d,edgeProcessX + 25, processY + 50, false);
            }

            if(e.getSourceEdge().isAProcess){

                short processId = e.getSourceEdge().getId();
                short resourceId = e.getDestinationEdge().getId();

                int edgeResourceX = nodeLocation.get(resourceId);
                int edgeProcessX = nodeLocation.get(processId);

                g.setColor(Color.RED);
                // +25 to make the arrow start in the middle, + 50 to make the arrow start from the bottom of the process
                g.drawLine(edgeResourceX + 25, resourceY, edgeProcessX + 25, processY + 50);
                //Draw the arrow
                this.drawArrowHead(g2d,edgeResourceX + 25, resourceY, true);
            }

        }

    }

    private void drawArrowHead(Graphics2D g2d, int x, int y, boolean isOutgoing) {
        int arrowSize = 10;  // Size of the arrow
        int[] xPoints, yPoints;

        if (isOutgoing) {
            // For outgoing arrow (pointing to the destination)
            xPoints = new int[] {x, x - arrowSize, x + arrowSize};
            yPoints = new int[] {y, y - arrowSize, y - arrowSize};
        } else {
            // For incoming arrow (pointing to the source)
            xPoints = new int[] {x, x - arrowSize, x + arrowSize};
            yPoints = new int[] {y, y + arrowSize, y + arrowSize};
        }

        // Draw the arrowhead (triangle)
        g2d.fillPolygon(xPoints, yPoints, 3);
    }

}
