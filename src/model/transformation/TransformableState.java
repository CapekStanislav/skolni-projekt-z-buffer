package model.transformation;

import transforms.Point3D;

/**
 * Instance rozhraní {@code TransformableState} představuje stav objektu,
 * který je transformovatelný. Pokud byl objekt transformován uloží se
 * tato informace do objektu implementujicí toto rozhraní. Eviduje se
 * škálování a rotace podle os X,Y,Z. Některé transformace jsou prováděny
 * dle středu objektu.
 *
 * @author Stanislav Čapek
 * @version 1.0
 */
public interface TransformableState {

    /**
     * Vrátí nastavený střed objektu
     *
     * @return střed objektu
     */
    Point3D getCenter();

    /**
     * Nastaví nový střed objektu
     *
     * @param point nový střed
     */
    void setCenter(Point3D point);

    /**
     * Informativní hodnota o velikosti objektu oproti defaultnímu stavu
     *
     * @return poměrová hodnota oproti původnímu stavu
     */
    double getScaleX();

    /**
     * Nastavení poměrové hodnoty - výchozí hodnota = 1
     *
     * @param ration poměr
     */
    void setScaleX(double ration);

    /**
     * Informativní hodnota o otočení objektu v radiánech - výchozí
     * hodnota = 0
     *
     * @return otočení objektu v radiánech
     */
    double getRotationX();

    /**
     * Nastavení hodnoty otočení v radiánech. Výchozí hodnota = 0
     *
     * @param radians otočení v radiánech
     */
    void setRotationX(double radians);

    /**
     * Informativní hodnota o velikosti objektu oproti defaultnímu stavu
     *
     * @return poměrová hodnota oproti původnímu stavu
     */
    double getScaleY();

    /**
     * Nastavení poměrové hodnoty - výchozí hodnota = 1
     *
     * @param ration poměr
     */
    void setScaleY(double ration);

    /**
     * Informativní hodnota o otočení objektu v radiánech - výchozí
     * hodnota = 0
     *
     * @return otočení objektu v radiánech
     */
    double getRotationY();

    /**
     * Nastavení hodnoty otočení v radiánech. Výchozí hodnota = 0
     *
     * @param radians otočení v radiánech
     */
    void setRotationY(double radians);

    /**
     * Informativní hodnota o velikosti objektu oproti defaultnímu stavu
     *
     * @return poměrová hodnota oproti původnímu stavu
     */
    double getScaleZ();

    /**
     * Nastavení poměrové hodnoty - výchozí hodnota = 1
     *
     * @param ration poměr
     */
    void setScaleZ(double ration);

    /**
     * Informativní hodnota o otočení objektu v radiánech - výchozí
     * hodnota = 0
     *
     * @return otočení objektu v radiánech
     */
    double getRotationZ();

    /**
     * Nastavení hodnoty otočení v radiánech. Výchozí hodnota = 0
     *
     * @param radians otočení v radiánech
     */
    void setRotationZ(double radians);
}
