//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.2 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2023.03.28 a las 11:12:47 AM COT 
//


package co.edu.upb.bucaramanga.java.multiplepdf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Batch" type="{http://java.bucaramanga.upb.edu.co/multiplepdf.xsd}BatchType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "batch"
})
@XmlRootElement(name = "getBatchRequest")
public class GetBatchRequest {

    @XmlElement(name = "Batch", required = true)
    protected BatchType batch;

    /**
     * Obtiene el valor de la propiedad batch.
     * 
     * @return
     *     possible object is
     *     {@link BatchType }
     *     
     */
    public BatchType getBatch() {
        return batch;
    }

    /**
     * Define el valor de la propiedad batch.
     * 
     * @param value
     *     allowed object is
     *     {@link BatchType }
     *     
     */
    public void setBatch(BatchType value) {
        this.batch = value;
    }

}
