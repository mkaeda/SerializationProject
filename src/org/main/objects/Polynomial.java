package org.main.objects;

public class Polynomial extends GeometricObject
{
    private int degree;
    private float[] roots;

    public Polynomial(int degree, float[] roots) {
    	if (degree < 0)
    		throw new IllegalArgumentException("degree must be 0 or higher");
    	if (roots == null)
    		throw new NullPointerException("roots");
    	// Even degree has between 0-degree number of roots
    	if (isEven(degree) && roots.length > degree)
    		// Empty aray has length == 0, so only need to consider if
    		// too many roots are given
    		throw new IllegalArgumentException("number of roots must must be between 0 and the degree, inclusive");
    	// Odd degree has between 1-degree number of roots
    	if (!isEven(degree) && (roots.length > degree || roots.length < 1))
    		throw new IllegalArgumentException("number of roots must must be between 1 and the degree, inclusive");
        this.degree	= degree;
        this.roots 	= roots;
    }

    public int getDegree() {
        return degree;
    }

    public float[] getRoots() {
        return roots;
    }
    
    private boolean isEven(int n)
    {
    	return n % 2 == 0;
    }
}
