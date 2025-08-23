START PROGRAM

SETUP:
Create a Window
Create a Canvas inside the window
Create a Graphics Context (for drawing)
Create a List of controlPoints (initially empty)
stepCounter = 0

EVENT HANDLING:
On Mouse Click (Left Button):
Add the clicked position to controlPoints
Redraw canvas showing all points as small circles

    On Key Press:
        IF key == ENTER AND controlPoints is not empty:
            IF size(controlPoints) == 1:
                Draw the single point
            ELSE IF size(controlPoints) == 2:
                Draw a line between the two points
            ELSE:
                Start Animation loop (Timeline or AnimationTimer)

        IF key == ESCAPE:
            Close the window

ANIMATION LOOP (when running):
Clear canvas
Draw controlPoints as circles
Apply Chaikin’s algorithm stepCounter times on controlPoints
Draw resulting curve (connect the points with lines)

    stepCounter += 1
    IF stepCounter > 7:
        stepCounter = 0   // restart cycle

BONUS (optional features):
On Key Press (like C for Clear):
Clear controlPoints list
Redraw empty canvas

    On Mouse Drag:
        If dragging near a control point:
            Move that point’s position
            Redraw

FUNCTION: chaikin(points):
newPoints = []
FOR each consecutive pair of points (P0, P1):
Q = 0.75*P0 + 0.25*P1
R = 0.25*P0 + 0.75*P1
Append Q and R to newPoints
RETURN newPoints

END PROGRAM
