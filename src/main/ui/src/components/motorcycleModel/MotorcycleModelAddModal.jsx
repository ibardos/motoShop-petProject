import {useEffect, useState} from "react";

import {Form} from "react-bootstrap";
import Button from "react-bootstrap/Button";

import CrudModal from "../shared/CrudModal";

import {fetchData} from "../../util/fetchData";
import {getJwtToken} from "../../security/authService";

// Imports related to form validation
import {Field, Formik} from "formik";
import * as Yup from "yup";


const MotorcycleModelAddModal = (props) => {
    const modalBody = <AddForm setFormSubmit={props.setFormSubmit} setAddModalShow={props.setAddModalShow} />

    return <CrudModal show={props.show} onHide={props.onHide} title="Add new Motorcycle model" body={modalBody} />
}


const AddForm = (props) => {
    const [manufacturers, setManufacturers] = useState([]);
    const [motorcycleModelTypes, setMotorcycleModelTypes] = useState([]);

    const [incompleteManufacturerNameAlert, setIncompleteManufacturerNameAlert] = useState(false);
    const [incompleteModelTypeAlert, setIncompleteModelTypeAlert] = useState(false);


    useEffect(() => {
        fetchData("/service/manufacturer/get/all")
            .then(result => setManufacturers(result));

        fetchData("/service/motorcycle/model/get/types")
            .then(result => setMotorcycleModelTypes(result));
    }, [])


    async function handleSubmit(values) {
        const url = "/service/motorcycle/model/add";

        const requestBody = {
            "manufacturer": manufacturers.find(manufacturer => manufacturer.name === values.manufacturerName),
            "modelName": values.modelName,
            "modelYear": values.modelYear,
            "weight": values.weight,
            "displacement": values.displacement,
            "horsePower": values.horsePower,
            "topSpeed": values.topSpeed,
            "gearbox": values.gearbox,
            "fuelCapacity": values.fuelCapacity,
            "fuelConsumptionPer100Kms": values.fuelConsumption,
            "motorcycleModelType": values.modelType
        }

        const options = {
            method: "POST",
            headers: { 'Content-Type': 'application/json', 'Authorization': `Bearer ${getJwtToken()}` },
            body: JSON.stringify(requestBody),
        }

        await fetch(url, options);
        props.setFormSubmit(old => !old);
    }


    const schema = Yup.object().shape({
        manufacturerName: Yup.string().required(),
        modelType: Yup.string().required(),
        modelName: Yup.string().required(),
        modelYear: Yup.number().max(new Date().getFullYear()+1, "maxNumber").required("Required"),
        weight: Yup.number().required(),
        displacement: Yup.number().required(),
        horsePower: Yup.number().required(),
        topSpeed: Yup.number().required(),
        gearbox: Yup.number().required(),
        fuelCapacity: Yup.number().required(),
        fuelConsumption: Yup.number().required()
    });


    const initialManufacturerName = "Click to select Manufacturer";
    const initialModelType = "Click to select Model type";

    return (
        <Formik
            enableReinitialize
            initialValues={{
                manufacturerName: initialManufacturerName,
                modelType: initialModelType,
                modelName: "",
                modelYear: "",
                weight: "",
                displacement: "",
                horsePower: "",
                topSpeed: "",
                gearbox: "",
                fuelCapacity: "",
                fuelConsumption: ""
            }}
            validationSchema={schema}
            onSubmit={async values => {
                if (values.manufacturerName === initialManufacturerName) {
                    setIncompleteManufacturerNameAlert(true)
                } else {
                    setIncompleteManufacturerNameAlert(false)
                }

                if (values.modelType === initialModelType) {
                    setIncompleteModelTypeAlert(true)
                } else {
                    setIncompleteModelTypeAlert(false)
                }


                if (values.manufacturerName !== initialManufacturerName && values.modelType !== initialModelType) {
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
                <Form.Group className="mb-3" controlId="formSelectManufacturerName">
                    <Form.Label>Manufacturer<span style={{color: "red"}}>*</span></Form.Label>
                    <br/>
                    <Field as="select"
                           name="manufacturerName"
                    >
                        <option value={initialManufacturerName}>{initialManufacturerName}</option>
                        {manufacturers.map(m => (
                            <option key={m.name} value={m.name}>{m.name}</option>
                        ))}
                    </Field>
                </Form.Group>
                <p className="formErrorText">{incompleteManufacturerNameAlert ? "Choose a Manufacturer!" : null}</p>

                <Form.Group className="mb-3" controlId="formSelectModelType">
                    <Form.Label>Model type<span style={{color: "red"}}>*</span></Form.Label>
                    <br/>
                    <Field as="select"
                           name="modelType"
                    >
                        <option value={initialModelType}>{initialModelType}</option>
                        {motorcycleModelTypes.map(type => (
                            <option key={type} value={type}>{type}</option>
                        ))}
                    </Field>
                </Form.Group>
                <p className="formErrorText">{incompleteModelTypeAlert ? "Choose a Model type!" : null}</p>


                <Form.Group className="mb-3" controlId="formTextareaModelName">
                    <Form.Label>Model name<span style={{color: "red"}}>*</span></Form.Label>
                    <Form.Control type="text"
                                  name="modelName"
                                  placeholder="DR-Z 400 SM"
                                  value={values.modelName}
                                  onChange={handleChange}
                                  onBlur={handleBlur}
                                  isValid={touched.modelName && !errors.modelName}
                                  isInvalid={touched.modelName && !!errors.modelName}/>
                </Form.Group>

                <Form.Group className="mb-3" controlId="formTextareaModelYear">
                    <Form.Label>Model year<span style={{color: "red"}}>*</span></Form.Label>
                        <Form.Control type="text"
                                      name="modelYear"
                                      placeholder="2005"
                                      value={values.modelYear}
                                      onChange={handleChange}
                                      onBlur={handleBlur}
                                      isValid={touched.modelYear && !errors.modelYear}
                                      isInvalid={touched.modelYear && !!errors.modelYear}/>
                </Form.Group>
                <div className="formErrorText">{errors.modelYear === "maxNumber" ? "No greater than next year!" : null}</div>

                <Form.Group className="mb-3" controlId="formTextareaWeight">
                    <Form.Label>Weight<span style={{color: "red"}}>*</span></Form.Label>
                    <Form.Control type="text"
                                  name="weight"
                                  placeholder="126"
                                  value={values.weight}
                                  onChange={handleChange}
                                  onBlur={handleBlur}
                                  isValid={touched.weight && !errors.weight}
                                  isInvalid={touched.weight && !!errors.weight}/>
                </Form.Group>

                <Form.Group className="mb-3" controlId="formTextareaDisplacement">
                    <Form.Label>Displacement<span style={{color: "red"}}>*</span></Form.Label>
                    <Form.Control type="text"
                                  name="displacement"
                                  placeholder="439"
                                  value={values.displacement}
                                  onChange={handleChange}
                                  onBlur={handleBlur}
                                  isValid={touched.displacement && !errors.displacement}
                                  isInvalid={touched.displacement && !!errors.displacement}/>
                </Form.Group>

                <Form.Group className="mb-3" controlId="formTextareaHorsePower">
                    <Form.Label>Horsepower<span style={{color: "red"}}>*</span></Form.Label>
                    <Form.Control type="text"
                                  name="horsePower"
                                  placeholder="62"
                                  value={values.horsePower}
                                  onChange={handleChange}
                                  onBlur={handleBlur}
                                  isValid={touched.horsePower && !errors.horsePower}
                                  isInvalid={touched.horsePower && !!errors.horsePower}/>
                </Form.Group>

                <Form.Group className="mb-3" controlId="formTextareaTopSpeed">
                    <Form.Label>Top speed<span style={{color: "red"}}>*</span></Form.Label>
                    <Form.Control type="text"
                                  name="topSpeed"
                                  placeholder="148"
                                  value={values.topSpeed}
                                  onChange={handleChange}
                                  onBlur={handleBlur}
                                  isValid={touched.topSpeed && !errors.topSpeed}
                                  isInvalid={touched.topSpeed && !!errors.topSpeed}/>
                </Form.Group>

                <Form.Group className="mb-3" controlId="formTextareaGearbox">
                    <Form.Label>Gearbox<span style={{color: "red"}}>*</span></Form.Label>
                    <Form.Control type="text"
                                  name="gearbox"
                                  placeholder="5"
                                  value={values.gearbox}
                                  onChange={handleChange}
                                  onBlur={handleBlur}
                                  isValid={touched.gearbox && !errors.gearbox}
                                  isInvalid={touched.gearbox && !!errors.gearbox}/>
                </Form.Group>

                <Form.Group className="mb-3" controlId="formTextareaFuelCapacity">
                    <Form.Label>Fuel capacity<span style={{color: "red"}}>*</span></Form.Label>
                    <Form.Control type="text"
                                  name="fuelCapacity"
                                  placeholder="10.0"
                                  value={values.fuelCapacity}
                                  onChange={handleChange}
                                  onBlur={handleBlur}
                                  isValid={touched.fuelCapacity && !errors.fuelCapacity}
                                  isInvalid={touched.fuelCapacity && !!errors.fuelCapacity}/>
                </Form.Group>

                <Form.Group className="mb-3" controlId="formTextareaFuelConsumption">
                    <Form.Label>Fuel consumption l/100km<span style={{color: "red"}}>*</span></Form.Label>
                    <Form.Control type="text"
                                  name="fuelConsumption"
                                  placeholder="4.2"
                                  value={values.fuelConsumption}
                                  onChange={handleChange}
                                  onBlur={handleBlur}
                                  isValid={touched.fuelConsumption && !errors.fuelConsumption}
                                  isInvalid={touched.fuelConsumption && !!errors.fuelConsumption}/>
                </Form.Group>


                <div style={{display: "flex", justifyContent: "center"}}>
                    <Button variant="secondary" style={{paddingInline: "30px"}} type="submit">Add</Button>
                </div>
            </Form>
            )}
        </Formik>
    )
}

export default MotorcycleModelAddModal;
