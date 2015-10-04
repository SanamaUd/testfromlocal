package java_project;


import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.pow;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * @author maud sananikone- s225004
 */
public class matrix implements java.io.Serializable {

    private int nrows;
    private int ncols;
    //Vector v = new Vector ();  --> class Vector is deprecated
    ArrayList<Double> v;// = new ArrayList<Double>();

    //here the constructors
    public matrix() {
        this.nrows = 0;
        this.ncols = 0;
        this.v = new ArrayList(); //creation of vector null
    }

    public matrix(int Nrows, int Ncols) {
        //check input
        if (Nrows < 0 || Ncols < 0) {
            throw new IllegalArgumentException("matrix size negative");
        }
        this.nrows = Nrows;
        this.ncols = Ncols;
        if (this.nrows <= 0 || this.ncols <= 0) {
            this.v = new ArrayList(0);  // empty matrix, nothing to allocate
        } else {
            this.v = new ArrayList(nrows * ncols);
        }
    }
    /*
     * Alternate constructor - creates a matrix from a vector
     */

    public matrix(final ArrayList x) {
        this.v = x;
        this.nrows = x.size();
        this.ncols = 1;
    }


    /*
     * Copy constructor
     */
    public matrix(final matrix m) {
        this.v = m.v;
        this.nrows = m.getNrows();
        this.ncols = m.getNcols();
    }
    /*
     * ACCESSOR METHOD
     * Get back the number of cols of matrix
     */

    public int getNcols() {
        return this.ncols;
    }
    /*
     * Get back the number of rows of matrix
     */

    public int getNrows() {
        return this.nrows;
    }

    /*
     * Operator= - assignment
     */
    public matrix assign(int i, int j, double value) {
        (this.v).add(i * (this.ncols) + j, value);

        return this;
    }

    public void show() {
        for (int i = 0; i < this.nrows; i++) {

            for (int j = 0; j < this.ncols; j++) {
                System.out.print((this.v).get(i * ncols + j) + " ");
            }
            System.out.println();
        }

    }

    public matrix multi(matrix factor) {

        //if the matrix sizes do not match
        if (this.getNcols() != factor.getNrows()) {
            throw new IllegalArgumentException("matrix sizes do not match");
        } else {

            matrix matrixResult = new matrix(this.nrows, factor.ncols);

            for (int i = 0; i < matrixResult.nrows; i++) {
                for (int j = 0; j < matrixResult.ncols; j++) {
                    (matrixResult.v).add(i * (this.ncols) + j, 0.0);
                }
            }

            for (int i = 0; i < nrows; i++) {
                for (int j = 0; j < factor.ncols; j++) {
                    double value = (matrixResult.v).get(i * (this.ncols) + j);
                    for (int k = 0; k < ncols; k++) {
                        value += ((this.v).get(i * (this.ncols) + k)) * ((factor.v).get(k * (factor.ncols) + j));
                        (matrixResult.v).set(i * (this.ncols) + j, value);
                    }
                }
            }

            return matrixResult;
        }
    }

    public ArrayList<Double> multi(final ArrayList<Double> vect) {

        //if the matrix sizes do not match
        if (this.ncols != vect.size()) {
            throw new IllegalArgumentException("matrix and vectorsizes do not match");
        } else {

            ArrayList<Double> res = new ArrayList();

            for (int i = 0; i < this.nrows; i++) {
                double value = 0.0;
                for (int j = 0; j < this.ncols; j++) {
                    value += ((this.v).get(i * nrows + j)) * (vect.get(j));
                }
                res.add(i, value);
            }
            // System.out.println("the size of the resultat vector: " + res.size());
            return res;
        }
    }

    public matrix compute_lower() {
        double mult;
        int i, j, k;

        matrix temp = new matrix(this.nrows, this.ncols);
//we copy the mathmatrix in the mathmatrix temp --------------------------------------------------------------------------------------------------

        for (i = 0; i < this.nrows; i++)// running the row
        {
            for (j = 0; j < this.ncols; j++)//running the column
            {
                //temp(i, j) = (*this)(i, j);
                double value = (this.v).get(i * (this.ncols) + j);
                (temp.v).add(i * (temp.ncols) + j, value);
            }
        }

        // LU (Doolittle's) decomposition without pivoting
        for (k = 0; k < this.nrows - 1; k++) {
            for (i = k + 1; i < this.nrows; i++) {
                if ((temp.v).get(k * (temp.ncols) + k) == 0) {
                    throw new IllegalArgumentException("pivot is zero\n");
                    //exit(1);
                }
                mult = ((temp.v).get(i * (temp.ncols) + k)) / ((temp.v).get(k * (temp.ncols) + k));
                (temp.v).set(i * (temp.ncols) + k, mult);
                for (j = k + 1; j < ncols; j++) {
                    double value = (temp.v).get(i * (temp.ncols) + j);
                    value -= mult * ((temp.v).get(k * (temp.ncols) + j));
                    (temp.v).set(i * (temp.ncols) + j, value);
                }
            }
        }
        // create l from temp
        matrix lower = new matrix(this.nrows, this.ncols);
        for (i = 0; i < nrows; i++) {
            for (j = 0; j < ncols; j++) {
                (lower.v).add(i * (lower.ncols) + j, 0.0);
            }
        }
        for (i = 0; i < nrows; i++) {
            (lower.v).set(i * (lower.ncols) + i, 1.0);
        }
        for (i = 1; i < nrows; i++) {
            for (j = 0; j < i; j++) {
                double value = (temp.v).get(i * (temp.ncols) + j);
                (lower.v).set(i * (lower.ncols) + j, value);
            }
        }
        return lower;
    }

    public matrix compute_upper() {
        double mult;
        int i, j, k;

        matrix temp = new matrix(this.nrows, this.ncols);
//we copy the mathmatrix in the mathmatrix temp --------------------------------------------------------------------------------------------------
        for (i = 0; i < this.nrows; i++)// running the row
        {
            for (j = 0; j < this.ncols; j++)//running the column
            {
                double value = (this.v).get(i * (this.ncols) + j);
                (temp.v).add(i * (temp.ncols) + j, value);
            }
        }

        // LU (Doolittle's) decomposition without pivoting
        for (k = 0; k < this.nrows - 1; k++) {
            for (i = k + 1; i < this.nrows; i++) {
                if ((temp.v).get(k * (temp.ncols) + k) == 0) {
                    throw new IllegalArgumentException("pivot is zero\n");
                    //exit(1);
                }
                mult = ((temp.v).get(i * (temp.ncols) + k)) / ((temp.v).get(k * (temp.ncols) + k));

                (temp.v).set(i * (temp.ncols) + k, mult);

                //  }// entries of L are saved in temp
                for (j = k + 1; j < ncols; j++) {
                    double value = (temp.v).get(i * (temp.ncols) + j);
                    value -= mult * ((temp.v).get(k * (temp.ncols) + j));
                    (temp.v).set(i * (temp.ncols) + j, value);
                }

            }
        }
        //create from upper from temp
        matrix upper = new matrix(this.nrows, this.ncols);

        for (i = 0; i < nrows; i++) {
            for (j = 0; j < ncols; j++) {
                (upper.v).add(i * (upper.ncols) + j, 0.0);
            }
        }
        for (i = 0; i < nrows; i++) {
            for (j = i; j < ncols; j++) {
                (upper.v).add(i * ncols + j, (temp.v).get(i * ncols + j));
            }
        }
        return upper;
    }

    public ArrayList<Double> lu_solve(matrix upper, ArrayList<Double> vect) {
        //double* temp;
        int i, j;
        int n = this.getNcols();
        //we copy the vector vect into the ArrayList temp
        ArrayList<Double> temp = new ArrayList();
        for (i = 0; i < vect.size(); i++) {
            temp.add(i, vect.get(i));
            //  System.out.println(temp.get(i));
        }
        // forward substitution for L y = b.

        for (i = 1; i < n; i++) {
            for (j = 0; j < i; j++) {
                //temp[i] -= l(i, j) * temp[j];
                double value = temp.get(i);
                value -= ((this.v).get(i * n + j)) * (temp.get(j));
                temp.set(i, value);
                // System.out.println(temp.get(i));
            }
        }
        // back substitution for U x = y.  
        for (i = n - 1; i >= 0; i--) {
            for (j = i + 1; j < n; j++) {
                double value = temp.get(i);
                value -= ((upper.v).get(i * n + j)) * (temp.get(j));
                temp.set(i, value);
            }
            double value_bis = temp.get(i);
            value_bis /= (upper.v).get(i * n + i);
            temp.set(i, value_bis);
        }

        // copy solution into x
        ArrayList<Double> x = new ArrayList();
        System.out.println("here the solution vector ");
        for (i = 0; i < n; i++) {
            x.add(i, temp.get(i));
            System.out.println(x.get(i));
        }
        return x;
    }

    //run on the matrix original
    public matrix reorder() {
// Note: pivoting information is stored in temperary vector pvt
// we have to return two object : the reordered matrix AND the reordered vector
        int i, j, k;
        int n = this.getNcols();

        ArrayList<Double> pvt = new ArrayList();
        int pvtk, pvti;
        ArrayList<Double> scale = new ArrayList();
        double aet, tmp, mult;
        matrix p = new matrix(n, n);
        matrix temp = new matrix(n, n);
// have to copy the matrix original into the matrix temp
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                (temp.v).add(i * n + j, (this.v).get(i * n + j));
            }
        }

//we set the value of the vector pvt
        for (k = 0; k < n; k++) {
            pvt.add(k, (double) k);
        }

//we create an empty solution matrix
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                (p.v).add(i * n + j, 0.0);
            }
        }

//we build the vector scale and set the value 
        for (k = 0; k < n; k++) {
            scale.add(k, 0.0);
        }
        for (k = 0; k < n; k++) {
            // scale.add(k, 0.0);
            for (j = 0; j < n; j++) {
                if (abs(scale.get(k)) < abs((temp.v).get(k * n + j))) {
                    scale.set(k, abs((temp.v).get(k * n + j)));
                }
            }
        }

        for (k = 0; k < n - 1; k++) {            // main elimination loop
// find the pivot in column k in rows pvt[k], pvt[k+1], ..., pvt[n-1]
            int pc = k;
            double value = pvt.get(k);
            int index = (int) value;
            aet = abs((temp.v).get(index * n + k) / scale.get(k));
            for (i = k + 1; i < n; i++) {

                double value_bis = pvt.get(i);
                int index_bis = (int) value_bis;
                tmp = abs((temp.v).get(index_bis * n + k) / scale.get(index_bis));
                if (tmp > aet) {
                    aet = tmp;
                    pc = i;
                }
            }
            if (aet == 0) {
                throw new IllegalArgumentException("pivot is zero 2\n");
            }
            if (pc != k) {
                // swap pvt[k] and pvt[pc]
                double value_ter = pvt.get(k);
                pvt.set(k, pvt.get(pc));
                pvt.set(pc, value_ter);
            }

// now eliminate the column entries logically below mx[pvt[k]][k]
            double value_quat = pvt.get(k);
            pvtk = (int) value_quat;                           // pivot row
            for (i = k + 1; i < n; i++) {
                double value_cinc = pvt.get(i);
                pvti = (int) value_cinc;
                if ((temp.v).get(pvti * n + k) != 0) {
                    mult = ((temp.v).get(pvti * n + k)) / ((temp.v).get(pvtk * n + k));
                    (temp.v).set(pvti * n + k, mult);

                    for (j = k + 1; j < n; j++) {
                        double value_six = (temp.v).get(pvti * n + j);
                        value_six -= mult * (temp.v).get(pvtk * n + j);
                        (temp.v).set(pvti * n + j, value_six);
                    }
                }
            }
        }

        for (i = 0; i < n; i++) {
            double value_last = pvt.get(i);
            int index_last = (int) value_last;
            (p.v).set(i * n + index_last, 1.0);
        }
        return p;
    }

    public ArrayList<Double> pivotArray(matrix p) {
        ArrayList<Double> pivot = new ArrayList();
        ArrayList<Double> result = new ArrayList();
        for (int i = 0; i < this.getNcols(); i++) {
            double value = (double) i + 1;
            pivot.add(i, value);
            //pivot.add(i,i+1);
        }
        //matrix p = new matrix(this.getNcols(),this.getNcols());
        p = this.reorder();
        result = p.multi(pivot);

        return result;
    }

    public matrix inverse(matrix lower, matrix upper) {
        //---------------------------------------------------------------------------------------------
        //first step, we find the permutation P 
        //---------------------------------------------------------------------------------------------
        // Note: pivoting information is stored in temperary vector pvt

        int i, j;

        int n = this.getNcols();
        matrix inverse = new matrix(n, n);
        for (int s = 0; s < n; s++) {
            for (j = 0; j < n; j++) {
                (inverse.v).add(s * n + j, 0.0);
            }
        }
        ArrayList<Double> b = new ArrayList();
        ArrayList<Double> x = new ArrayList();
        for (int s = 0; s < n; s++) {
            x.add(s, 0.0);
        }
        int t, s;

        for (i = 0; i < n; i++) {
            b.add(i, 1.0);
        }

        for (i = 0; i < n; i++) {
            b.set(i, 1.0);
            ArrayList<Double> tempvect = new ArrayList();
            for (t = 0; t < n; t++)// running the row
            {

                tempvect.add(t, b.get(t));
            }

            // forward substitution for L y = b.
            for (t = 1; t < n; t++) {
                for (s = 0; s < t; s++) {
                    //tempvect[t] -= lowerMatrix(t, s) * tempvect[s];
                    double value_quat = tempvect.get(t);
                    value_quat -= ((lower.v).get(t * n + s)) * tempvect.get(s);
                    tempvect.set(t, value_quat);
                }
            }

            // back substitution for U x = y.  
            for (t = n - 1; t >= 0; t--) {
                for (s = t + 1; s < n; s++) {
                    //tempvect[t] -= upperMatrix(t, s) * tempvect[s];
                    double valueCin = tempvect.get(t);
                    valueCin -= ((upper.v).get(t * n + s)) * tempvect.get(s);
                    tempvect.set(t, valueCin);

                }
                //tempvect[t] /= upperMatrix(t, t);
                double valueSix = tempvect.get(t);
                valueSix /= (upper.v).get(t * n + t);
                tempvect.set(t, valueSix);
            }

            // copy solution into x
            for (t = 0; t < n; t++) {
                //x[t] = tempvect[t];
                x.set(t, tempvect.get(t));
                //x.add(t, tempvect.get(t));
            }

            //------------------------------------------------------
            for (s = 0; s < n; s++) {
                //inverse(s, i) = x[s];
                (inverse.v).set(s * n + i, x.get(s));

            }
            b.set(i, 0.0);

        }

        return inverse;

    }

    public String matrixIntoString(DecimalFormat df) {
        int rows = this.getNrows();
        int cols = this.getNcols();

        //DecimalFormat df = new DecimalFormat("####0.00000000");
        String matrixString = "";
        for (int i = 0; i < rows; i++) {

            for (int j = 0; j < cols; j++) {
                String value = df.format((this.v).get(i * cols + j));
                matrixString = matrixString + "  " + value;
            }
            matrixString = matrixString + "\n";
        }

        return matrixString;
    }

    //we calculate the matrix |A-XI|, X is unknown vector
    public double calculateDeter(matrix upper) {
        double det = 1;
        for (int i = 0; i < this.getNcols(); i++) {
            //for(int j=0;j<this.getNcols();j++){
            det = det * ((upper.v).get(i * this.getNcols() + i));
            //}
            System.out.println(det);
        }
        double per = this.numberPermutation();
        det = pow(-1.0, per) * det;
        return det;

    }

    public double numberPermutation() {
        int i, j, k;
        double permutation = 0.0;
        int n = this.getNcols();

        ArrayList<Double> pvt = new ArrayList();
        int pvtk, pvti;
        ArrayList<Double> scale = new ArrayList();
        double aet, tmp, mult;
        matrix p = new matrix(n, n);
        matrix temp = new matrix(n, n);
// have to copy the matrix original into the matrix temp
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                (temp.v).add(i * n + j, (this.v).get(i * n + j));
            }
        }

//we set the value of the vector pvt
        for (k = 0; k < n; k++) {
            pvt.add(k, (double) k);
        }

//we create an empty solution matrix
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                (p.v).add(i * n + j, 0.0);
            }
        }

//we build the vector scale and set the value 
        for (k = 0; k < n; k++) {
            scale.add(k, 0.0);
        }
        for (k = 0; k < n; k++) {
            // scale.add(k, 0.0);
            for (j = 0; j < n; j++) {
                if (abs(scale.get(k)) < abs((temp.v).get(k * n + j))) {
                    scale.set(k, abs((temp.v).get(k * n + j)));
                }
            }
        }

        for (k = 0; k < n - 1; k++) {            // main elimination loop
// find the pivot in column k in rows pvt[k], pvt[k+1], ..., pvt[n-1]
            int pc = k;
            double value = pvt.get(k);
            int index = (int) value;
            aet = abs((temp.v).get(index * n + k) / scale.get(k));
            for (i = k + 1; i < n; i++) {

                double value_bis = pvt.get(i);
                int index_bis = (int) value_bis;
                tmp = abs((temp.v).get(index_bis * n + k) / scale.get(index_bis));
                if (tmp > aet) {
                    aet = tmp;
                    pc = i;
                    permutation++;
                }
            }
        }
        return permutation;

    }

    public boolean singularTest(matrix upper) {
        //matrix upper = new matrix(this.getNcols(), this.getNcols());
        //upper = this.compute_lower();
        double det = this.calculateDeter(upper);
        if (det == 0) {
            return false;
        }
        return true;
    }

}
