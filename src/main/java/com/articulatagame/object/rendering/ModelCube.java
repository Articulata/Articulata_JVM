package com.articulatagame.object.rendering;

import static org.lwjgl.opengl.GL11.*;

public class ModelCube extends Model {

    float angle = 2;

    float[][] vertsZ = {
            {-0.5f, 0.5f, -0.5f}, /* 0 left top rear */
            {0.5f, 0.5f, -0.5f},   /* 1 right top rear */
            {0.5f, -0.5f, -0.5f},   /* 2 right bottom rear */
            {-0.5f, -0.5f, -0.5f},   /* 3 left bottom rear */
            {-0.5f, 0.5f, 0.5f},   /* 4 left top front */
            {0.5f, 0.5f, 0.5f},   /* 5 right top front */
            {0.5f, -0.5f, 0.5f},   /* 6 right bottom front */
            {-0.5f, -0.5f, 0.5f}    /* 7 left bottom front */
    };

    int[][] facesZ = {
            {7, 6, 5, 4},
            {6, 2, 1, 5},
            {3, 7, 4, 0},
            {5, 1, 0, 4},
            {7, 6, 2, 3},
            {2, 3, 0, 1}
    };

    int[][] colors = {
            {255, 0, 0},
            {0, 255, 0},
            {0, 0, 255},
            {255, 0, 255},
            {125, 255, 0},
            {0, 125, 255}
    };

    public ModelCube() {

    }

    @Override public void render(int x, int y, int z) {
        glLoadIdentity();

        glRotated(angle, 0, 1, 0);
        glRotated(angle / 2, 1, 0, 0);
        angle += 0.5;
        for (int face = 0; face < 6; face++) {

            glBegin(GL_QUADS);
            glColor3f(colors[face][0], colors[face][1], colors[face][2]);

            for (int vert = 0; vert < 4; vert++) {
                glVertex3fv(vertsZ[facesZ[face][vert]]);
            }
            glEnd();

        }
    }

    public void destroy() {
    }
}
