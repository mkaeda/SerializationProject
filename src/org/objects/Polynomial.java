package org.objects;

public class Polynomial extends GeometricObject
{
    private int degree;
    private float[] roots;

    public Polynomial(int degree, float[] roots) {
        this.degree = degree;
        this.roots = roots;
    }

    // Setters
    public void setDegree(int degree) {
        this.degree = degree;
    }

    public void setRoots(float[] roots) {
        this.roots = roots;
    }

    // Getters
    public int getDegree() {
        return degree;
    }

    public float[] getRoots() {
        return roots;
    }
}
