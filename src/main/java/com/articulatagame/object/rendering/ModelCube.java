package com.articulatagame.object.rendering;

import static org.lwjgl.opengl.GL11.*;

public class ModelCube extends Model {

    private float[][] vertices;
    private float angle1 = 0.0f;
    private float angle2 = 0.0f;
    private int[][] indices;

    public ModelCube() {
        indices = new int[][] {
                {5, 4},
                {5, 0},

                {4, 7},
                {7, 0},

                {6, 5},
                {1, 0},

                {3, 4},
                {2, 7},

                {6, 3},
                {6, 1},

                {3, 2},
                {2, 1}
        };
        vertices = new float[][] {
                {1.0f, 1.0f, 1.0f}, //0
                {1.0f, 1.0f, -1.0f}, //1
                {1.0f, -1.0f, -1.0f}, //2
                {-1.0f, -1.0f, -1.0f}, //3
                {-1.0f, -1.0f, 1.0f}, //4
                {-1.0f, 1.0f, 1.0f}, //5
                {-1.0f, 1.0f, -1.0f}, //6
                {1.0f, -1.0f, 1.0f}  //7
        };

    }

    @Override public void render(int x, int y, int z) {
        glLoadIdentity();
        glTranslatef(0.5f, 0.0f, -0f);
        glScalef(100, 100, 100);
        glBegin(GL_QUADS);
        glColor3f(0.0f, 1.0f, 0.0f);
        glVertex3f(1.0f, 1.0f, -1.0f);
        glVertex3f(-1.0f, 1.0f, -1.0f);
        glVertex3f(-1.0f, 1.0f, 1.0f);
        glVertex3f(1.0f, 1.0f, 1.0f);

        glColor3f(1.0f, 0.5f, 0.0f);
        glVertex3f(1.0f, -1.0f, 1.0f);
        glVertex3f(-1.0f, -1.0f, 1.0f);
        glVertex3f(-1.0f, -1.0f, -1.0f);
        glVertex3f(1.0f, -1.0f, -1.0f);

        glColor3f(1.0f, 0.0f, 0.0f);
        glVertex3f(1.0f, 1.0f, 1.0f);
        glVertex3f(-1.0f, 1.0f, 1.0f);
        glVertex3f(-1.0f, -1.0f, 1.0f);
        glVertex3f(1.0f, -1.0f, 1.0f);

        glColor3f(1.0f, 1.0f, 0.0f);
        glVertex3f(1.0f, -1.0f, -1.0f);
        glVertex3f(-1.0f, -1.0f, -1.0f);
        glVertex3f(-1.0f, 1.0f, -1.0f);
        glVertex3f(1.0f, 1.0f, -1.0f);

        glColor3f(0.0f, 0.0f, 1.0f);
        glVertex3f(-1.0f, 1.0f, 1.0f);
        glVertex3f(-1.0f, 1.0f, -1.0f);
        glVertex3f(-1.0f, -1.0f, -1.0f);
        glVertex3f(-1.0f, -1.0f, 1.0f);

        glColor3f(1.0f, 0.0f, 1.0f);
        glVertex3f(1.0f, 1.0f, -1.0f);
        glVertex3f(1.0f, 1.0f, 1.0f);
        glVertex3f(1.0f, -1.0f, 1.0f);
        glVertex3f(1.0f, -1.0f, -1.0f);
        glEnd();
    }
}
