import {useEffect, useState} from "react";

import {Form} from "react-bootstrap";
import Button from "react-bootstrap/Button";

import CrudModal from "../shared/CrudModal";

import {fetchData} from "../../util/fetchData";

// Imports related to form validation
import {Field, Formik} from "formik";
import * as Yup from "yup";


const MotorcycleStockUpdateModal = (props) => {
    const modalBody = <UpdateForm motorcycleStocks={props.motorcycleStocks} recordId={props.recordId}
                                  setFormSubmit={props.setFormSubmit} setUpdateModalShow={props.setUpdateModalShow}/>

    return <CrudModal show={props.show} onHide={props.onHide} title="Update Motorcycle in stock" body={modalBody}/>
}


const UpdateForm = (props) => {
    const [motorcycleModels, setMotorcycleModels] = useState([]);

    const [currentRecord, setCurrentRecord] = useState({});
    const [currentMotorcycleModel, setCurrentMotorcycleModel] = useState({});


    useEffect(() => {
        fetchData("/motorcycle/model/get/all")
            .then(result => setMotorcycleModels(result));

        const currentRecord = props.motorcycleStocks.find(m => m.id.toString() === props.recordId);

        setCurrentRecord(currentRecord);
        setCurrentMotorcycleModel(currentRecord.motorcycleModel);
    }, [])


    async function handleSubmit(values) {
        const url = "/motorcycle/stock/update";

        const requestBody = {
            "id": currentRecord.id,
            "motorcycleModel": values.motorcycleModel ? motorcycleModels.find(motorcycleModel => motorcycleModel.modelName === values.motorcycleModel) : currentMotorcycleModel,
            "mileage": values.mileage ? values.mileage : currentRecord.mileage,
            "purchasingPrice": values.purchasingPrice ? values.purchasingPrice : currentRecord.purchasingPrice,
            "profitMargin": values.profitMargin ? values.profitMargin : currentRecord.profitMargin,
            "inStock": values.inStock ? values.inStock : currentRecord.inStock,
            "color": values.color ? values.color : currentRecord.color
        }

        const options = {
            method: "PUT",
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestBody),
        }

        await fetch(url, options);
        props.setFormSubmit(old => !old);
    }


    const schema = Yup.object().shape({
        motorcycleModel: Yup.string().required(),
        mileage: Yup.number().required(),
        purchasingPrice: Yup.number().required(),
        profitMargin: Yup.number().required(),
        inStock: Yup.number().required(),
        color: Yup.string().required()
    });


    return (
        <Formik
            enableReinitialize
            initialValues={{
                motorcycleModel: currentMotorcycleModel.modelName,
                mileage: currentRecord.mileage,
                purchasingPrice: currentRecord.purchasingPrice,
                profitMargin: currentRecord.profitMargin,
                inStock: currentRecord.inStock,
                color: currentRecord.color
            }}
            validationSchema={schema}
            onSubmit={async values => {
                await handleSubmit(values)
                props.setUpdateModalShow(false)
            }}
        >
            {({
                  handleSubmit,
                  handleChange,
                  handleBlur,
                  values,
                  errors,
              }) => (
                <Form onSubmit={handleSubmit}>
                    <Form.Group className="mb-3" controlId="formSelectMotorcycleModel">
                        <Form.Label>Motorcycle model<span style={{color: "red"}}>*</span></Form.Label>
                        <br/>
                        <Field as="select"
                               name="motorcycleModel"
                        >
                            <option value={values.motorcycleModel}>{values.motorcycleModel}</option>
                            {motorcycleModels.map(m => {
                                if (m.modelName !== values.motorcycleModel) {
                                    return <option key={m.modelName} value={m.modelName}>{m.modelName}</option>
                                }
                            })}
                        </Field>
                    </Form.Group>


                    <Form.Group className="mb-3" controlId="formTextareaMileage">
                        <Form.Label>Mileage<span style={{color: "red"}}>*</span></Form.Label>
                        <Form.Control type="text"
                                      name="mileage"
                                      placeholder="38000"
                                      defaultValue={values.mileage}
                                      onChange={handleChange}
                                      onBlur={handleBlur}
                                      isValid={!errors.mileage}
                                      isInvalid={!!errors.mileage}/>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formTextareaPurchasingPrice">
                        <Form.Label>Purchasing price<span style={{color: "red"}}>*</span></Form.Label>
                        <Form.Control type="text"
                                      name="purchasingPrice"
                                      placeholder="4080"
                                      defaultValue={values.purchasingPrice}
                                      onChange={handleChange}
                                      onBlur={handleBlur}
                                      isValid={!errors.purchasingPrice}
                                      isInvalid={!!errors.purchasingPrice}/>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formTextareaProfitMargin">
                        <Form.Label>Profit margin<span style={{color: "red"}}>*</span></Form.Label>
                        <Form.Control type="text"
                                      name="profitMargin"
                                      placeholder="0.1"
                                      defaultValue={values.profitMargin}
                                      onChange={handleChange}
                                      onBlur={handleBlur}
                                      isValid={!errors.profitMargin}
                                      isInvalid={!!errors.profitMargin}/>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formTextareaInStock">
                        <Form.Label>In stock<span style={{color: "red"}}>*</span></Form.Label>
                        <Form.Control type="text"
                                      name="inStock"
                                      placeholder="1"
                                      defaultValue={values.inStock}
                                      onChange={handleChange}
                                      onBlur={handleBlur}
                                      isValid={!errors.inStock}
                                      isInvalid={!!errors.inStock}/>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formTextareaColor">
                        <Form.Label>Color<span style={{color: "red"}}>*</span></Form.Label>
                        <Form.Control type="text"
                                      name="color"
                                      placeholder="Black"
                                      defaultValue={values.color}
                                      onChange={handleChange}
                                      onBlur={handleBlur}
                                      isValid={!errors.color}
                                      isInvalid={!!errors.color}/>
                    </Form.Group>


                    <div style={{display: "flex", justifyContent: "center"}}>
                        <Button variant="secondary" style={{paddingInline: "30px"}} type="submit">Update</Button>
                    </div>
                </Form>
            )}
        </Formik>
    )
}

export default MotorcycleStockUpdateModal;