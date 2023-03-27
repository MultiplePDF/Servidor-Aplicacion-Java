//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.2 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2023.03.26 a las 07:03:35 PM COT 
//


package co.edu.upb.bucaramanga.java.multiplepdf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para BatchType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="BatchType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IdBatch" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IdUser" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DateCreated" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="FileQuantity" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Path" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="TimeExpiration" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="State" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BatchType", propOrder = {
    "idBatch",
    "idUser",
    "dateCreated",
    "fileQuantity",
    "path",
    "timeExpiration",
    "state"
})
public class BatchType {

    @XmlElement(name = "IdBatch", required = true)
    protected String idBatch;
    @XmlElement(name = "IdUser", required = true)
    protected String idUser;
    @XmlElement(name = "DateCreated", required = true)
    protected String dateCreated;
    @XmlElement(name = "FileQuantity", required = true)
    protected String fileQuantity;
    @XmlElement(name = "Path", required = true)
    protected String path;
    @XmlElement(name = "TimeExpiration", required = true)
    protected String timeExpiration;
    @XmlElement(name = "State", required = true)
    protected String state;

    /**
     * Obtiene el valor de la propiedad idBatch.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdBatch() {
        return idBatch;
    }

    /**
     * Define el valor de la propiedad idBatch.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdBatch(String value) {
        this.idBatch = value;
    }

    /**
     * Obtiene el valor de la propiedad idUser.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdUser() {
        return idUser;
    }

    /**
     * Define el valor de la propiedad idUser.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdUser(String value) {
        this.idUser = value;
    }

    /**
     * Obtiene el valor de la propiedad dateCreated.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateCreated() {
        return dateCreated;
    }

    /**
     * Define el valor de la propiedad dateCreated.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateCreated(String value) {
        this.dateCreated = value;
    }

    /**
     * Obtiene el valor de la propiedad fileQuantity.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileQuantity() {
        return fileQuantity;
    }

    /**
     * Define el valor de la propiedad fileQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileQuantity(String value) {
        this.fileQuantity = value;
    }

    /**
     * Obtiene el valor de la propiedad path.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPath() {
        return path;
    }

    /**
     * Define el valor de la propiedad path.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPath(String value) {
        this.path = value;
    }

    /**
     * Obtiene el valor de la propiedad timeExpiration.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeExpiration() {
        return timeExpiration;
    }

    /**
     * Define el valor de la propiedad timeExpiration.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeExpiration(String value) {
        this.timeExpiration = value;
    }

    /**
     * Obtiene el valor de la propiedad state.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getState() {
        return state;
    }

    /**
     * Define el valor de la propiedad state.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setState(String value) {
        this.state = value;
    }

}
