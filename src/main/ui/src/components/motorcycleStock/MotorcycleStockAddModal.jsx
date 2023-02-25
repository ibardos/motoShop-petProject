import {useEffect, useState} from "react";

import {Form} from "react-bootstrap";
import Button from "react-bootstrap/Button";

import CrudModal from "../shared/CrudModal";

import {fetchData} from "../../util/fetchData";

// Imports related to form validation
import {Field, Formik} from "formik";
import * as Yup from "yup";


const MotorcycleStockAddModal = (props) => {
    const modalBody = <AddForm setFormSubmit={props.setFormSubmit} setAddModalShow={props.setAddModalShow} />

    return <CrudModal show={props.show} onHide={props.onHide} title="Add new Motorcycle to stock" body={modalBody} />
}


const AddForm = (props) => {
    const [motorcycleModels, setMotorcycleModels] = useState([]);

    const [incompleteMotorcycleModelAlert, setIncompleteMotorcycleModelAlert] = useState(false);


    useEffect(() => {
        fetchData("/motorcycle/model/get/all")
            .then(result => setMotorcycleModels(result));
    }, [])


    async function handleSubmit(values) {
        const url = "/motorcycle/stock/add";

        const requestBody = {
            "motorcycleModel": motorcycleModels.find(motorcycleModel => motorcycleModel.modelName === values.motorcycleModel),
            "mileage": values.mileage,
            "purchasingPrice": values.purchasingPrice,
            "profitMargin": values.profitMargin,
            "inStock": values.inStock,
            "color": values.color
        }

        const options = {
            method: "POST",
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


    const initialMotorcycleModel = "Click to select Motorcycle model";

    return (
        <Formik
            enableReinitialize
            initialValues={{
                motorcycleModel: initialMotorcycleModel,
                mileage: "",
                purchasingPrice: "",
                profitMargin: "",
                inStock: "",
                color: ""
            }}
            validationSchema={schema}
            onSubmit={async values => {
                if (values.motorcycleModel === initialMotorcycleModel) {
                    setIncompleteMotorcycleModelAlert(true)
                } else {
                    setIncompleteMotorcycleModelAlert(false)
                }

                if (values.motorcycleModel !== initialMotorcycleModel) {
                    await handleSubmit(values)
                    props.setAddModalShow(false)
                }
            }}
        >
            {({
                  handleSubmit,
                  handleChange,
                  handleBlur,
                  values,
                  touched,
                  errors,
              }) => (
            <Form onSubmit={handleSubmit}>
                <Form.Group className="mb-3" controlId="formSelectMotorcycleModel">
                    <Form.Label>Motorcycle model<span style={{color: "red"}}>*</span></Form.Label>
                    <br/>
                    <Field as="select"
                           name="motorcycleModel"
                    >
                        <option value={initialMotorcycleModel}>{initialMotorcycleModel}</option>
                        {motorcycleModels.map(m => (
                            <option key={m.modelName} value={m.modelName}>{m.modelName}</option>
                        ))}
                    </Field>
                </Form.Group>
                <p className="formErrorText">{incompleteMotorcycleModelAlert ? "Choose a Motorcycle model!" : null}</p>


                <Form.Group className="mb-3" controlId="formTextareaMileage">
                    <Form.Label>Mileage<span style={{color: "red"}}>*</span></Form.Label>
                    <Form.Control type="text"
                                  name="mileage"
                                  placeholder="38000"
                                  value={values.mileage}
                                  onChange={handleChange}
                                  onBlur={handleBlur}
                                  isValid={touched.mileage && !errors.mileage}
                                  isInvalid={touched.mileage && !!errors.mileage}/>
                </Form.Group>

                <Form.Group className="mb-3" controlId="formTextareaPurchasingPrice">
                    <Form.Label>Purchasing price<span style={{color: "red"}}>*</span></Form.Label>
                        <Form.Control type="text"
                                      name="purchasingPrice"
                                      placeholder="4080"
                                      value={values.purchasingPrice}
                                      onChange={handleChange}
                                      onBlur={handleBlur}
                                      isValid={touched.purchasingPrice && !errors.purchasingPrice}
                                      isInvalid={touched.purchasingPrice && !!errors.purchasingPrice}/>
                </Form.Group>

                <Form.Group className="mb-3" controlId="formTextareaProfitMargin">
                    <Form.Label>Profit margin<span style={{color: "red"}}>*</span></Form.Label>
                    <Form.Control type="text"
                                  name="profitMargin"
                                  placeholder="0.1"
                                  value={values.profitMargin}
                                  onChange={handleChange}
                                  onBlur={handleBlur}
                                  isValid={touched.profitMargin && !errors.profitMargin}
                                  isInvalid={touched.profitMargin && !!errors.profitMargin}/>
                </Form.Group>

                <Form.Group className="mb-3" controlId="formTextareaInStock">
                    <Form.Label>In stock<span style={{color: "red"}}>*</span></Form.Label>
                    <Form.Control type="text"
                                  name="inStock"
                                  placeholder="1"
                                  value={values.inStock}
                                  onChange={handleChange}
                                  onBlur={handleBlur}
                                  isValid={touched.inStock && !errors.inStock}
                                  isInvalid={touched.inStock && !!errors.inStock}/>
                </Form.Group>

                <Form.Group className="mb-3" controlId="formTextareaColor">
                    <Form.Label>Color<span style={{color: "red"}}>*</span></Form.Label>
                    <Form.Control type="text"
                                  name="color"
                                  placeholder="Black"
                                  value={values.color}
                                  onChange={handleChange}
                                  onBlur={handleBlur}
                                  isValid={touched.color && !errors.color}
                                  isInvalid={touched.color && !!errors.color}/>
                </Form.Group>


                <div style={{display: "flex", justifyContent: "center"}}>
                    <Button variant="secondary" style={{paddingInline: "30px"}} type="submit">Add</Button>
                </div>
            </Form>
            )}
        </Formik>
    )
}

export default MotorcycleStockAddModal;