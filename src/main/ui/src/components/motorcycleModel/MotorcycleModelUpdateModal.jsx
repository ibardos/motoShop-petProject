import {useEffect, useState} from "react";

import {Form} from "react-bootstrap";
import Button from "react-bootstrap/Button";

import CrudModal from "../shared/CrudModal";

import {fetchData} from "../../util/fetchData";

// Imports related to form validation
import {Field, Formik} from "formik";
import * as Yup from "yup";


const MotorcycleModelUpdateModal = (props) => {
    const modalBody = <UpdateForm motorcycleModels={props.motorcycleModels} recordId={props.recordId}
                                  setFormSubmit={props.setFormSubmit} setUpdateModalShow={props.setUpdateModalShow}/>

    return <CrudModal show={props.show} onHide={props.onHide} title="Update Motorcycle model" body={modalBody}/>
}


const UpdateForm = (props) => {
    const [manufacturers, setManufacturers] = useState([]);
    const [motorcycleModelTypes, setMotorcycleModelTypes] = useState([]);

    const [currentRecord, setCurrentRecord] = useState({});
    const [currentManufacturer, setCurrentManufacturer] = useState({});


    useEffect(() => {
        fetchData("/manufacturer/get/all")
            .then(result => setManufacturers(result));

        fetchData("/motorcycle/model/get/types")
            .then(result => setMotorcycleModelTypes(result));

        const currentRecord = props.motorcycleModels.find(m => m.id.toString() === props.recordId);

        setCurrentRecord(currentRecord);
        setCurrentManufacturer(currentRecord.manufacturer);
    }, [props.motorcycleModels, props.recordId])


    async function handleSubmit(values) {
        const url = "/motorcycle/model/update";

        const requestBody = {
            "id": currentRecord.id,
            "manufacturer": values.manufacturerName ? manufacturers.find(manufacturer => manufacturer.name === values.manufacturerName) : currentManufacturer,
            "modelName": values.modelName ? values.modelName : currentRecord.modelName,
            "modelYear": values.modelYear ? values.modelYear : currentRecord.modelYear,
            "weight": values.weight ? values.weight : currentRecord.weight,
            "displacement": values.displacement ? values.displacement : currentRecord.displacement,
            "horsePower": values.horsePower ? values.horsePower : currentRecord.horsePower,
            "topSpeed": values.topSpeed ? values.topSpeed : currentRecord.topSpeed,
            "gearbox": values.gearbox ? values.gearbox : currentRecord.gearbox,
            "fuelCapacity": values.fuelCapacity ? values.fuelCapacity : currentRecord.fuelCapacity,
            "fuelConsumptionPer100Kms": values.fuelConsumption ? values.fuelConsumption : currentRecord.fuelConsumption,
            "motorcycleModelType": values.modelType ? values.modelType : currentRecord.modelType
        }

        const options = {
            method: "PUT",
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(requestBody),
        }

        await fetch(url, options);
        props.setFormSubmit(old => !old);
    }


    const schema = Yup.object().shape({
        manufacturerName: Yup.string().required(),
        modelType: Yup.string().required(),
        modelName: Yup.string().required(),
        modelYear: Yup.number().max(new Date().getFullYear() + 1, "maxNumber").required("Required"),
        weight: Yup.number().required(),
        displacement: Yup.number().required(),
        horsePower: Yup.number().required(),
        topSpeed: Yup.number().required(),
        gearbox: Yup.number().required(),
        fuelCapacity: Yup.number().required(),
        fuelConsumption: Yup.number().required()
    });


    return (
        <Formik
            enableReinitialize
            initialValues={{
                manufacturerName: currentManufacturer.name,
                modelType: currentRecord.motorcycleModelType,
                modelName: currentRecord.modelName,
                modelYear: currentRecord.modelYear,
                weight: currentRecord.weight,
                displacement: currentRecord.displacement,
                horsePower: currentRecord.horsePower,
                topSpeed: currentRecord.topSpeed,
                gearbox: currentRecord.gearbox,
                fuelCapacity: currentRecord.fuelCapacity,
                fuelConsumption: currentRecord.fuelConsumptionPer100Kms
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
                    <Form.Group className="mb-3" controlId="formSelectManufacturerName">
                        <Form.Label>Manufacturer<span style={{color: "red"}}>*</span></Form.Label>
                        <br/>
                        <Field as="select"
                               name="manufacturerName"
                        >
                            <option value={values.manufacturerName}>{values.manufacturerName}</option>
                            {manufacturers.map(m => {
                                if (m.name !== values.manufacturerName) {
                                    return <option key={m.name} value={m.name}>{m.name}</option>
                                }

                                return "Click to select Manufacturer";
                            })}
                        </Field>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formSelectModelType">
                        <Form.Label>Model type<span style={{color: "red"}}>*</span></Form.Label>
                        <br/>
                        <Field as="select"
                               name="modelType"
                        >
                            <option value={values.modelType}>{values.modelType}</option>
                            {motorcycleModelTypes.map(type => {
                                if (type !== values.modelType) {
                                    return <option key={type} value={type}>{type}</option>
                                }

                                return "Click to select Model type";
                            })}
                        </Field>
                    </Form.Group>


                    <Form.Group className="mb-3" controlId="formTextareaModelName">
                        <Form.Label>Model name<span style={{color: "red"}}>*</span></Form.Label>
                        <Form.Control type="text"
                                      name="modelName"
                                      placeholder="DR-Z 400 SM"
                                      defaultValue={values.modelName}
                                      onChange={handleChange}
                                      onBlur={handleBlur}
                                      isValid={!errors.modelName}
                                      isInvalid={!!errors.modelName}/>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formTextareaModelYear">
                        <Form.Label>Model year<span style={{color: "red"}}>*</span></Form.Label>
                        <Form.Control type="text"
                                      name="modelYear"
                                      placeholder="2005"
                                      defaultValue={values.modelYear}
                                      onChange={handleChange}
                                      onBlur={handleBlur}
                                      isValid={!errors.modelYear}
                                      isInvalid={!!errors.modelYear}/>
                    </Form.Group>
                    <div
                        className="formErrorText">{errors.modelYear === "maxNumber" ? "No greater than next year!" : null}</div>

                    <Form.Group className="mb-3" controlId="formTextareaWeight">
                        <Form.Label>Weight<span style={{color: "red"}}>*</span></Form.Label>
                        <Form.Control type="text"
                                      name="weight"
                                      placeholder="126"
                                      defaultValue={values.weight}
                                      onChange={handleChange}
                                      onBlur={handleBlur}
                                      isValid={!errors.weight}
                                      isInvalid={!!errors.weight}/>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formTextareaDisplacement">
                        <Form.Label>Displacement<span style={{color: "red"}}>*</span></Form.Label>
                        <Form.Control type="text"
                                      name="displacement"
                                      placeholder="439"
                                      defaultValue={values.displacement}
                                      onChange={handleChange}
                                      onBlur={handleBlur}
                                      isValid={!errors.displacement}
                                      isInvalid={!!errors.displacement}/>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formTextareaHorsePower">
                        <Form.Label>Horse power<span style={{color: "red"}}>*</span></Form.Label>
                        <Form.Control type="text"
                                      name="horsePower"
                                      placeholder="62"
                                      defaultValue={values.horsePower}
                                      onChange={handleChange}
                                      onBlur={handleBlur}
                                      isValid={!errors.horsePower}
                                      isInvalid={!!errors.horsePower}/>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formTextareaTopSpeed">
                        <Form.Label>Top speed<span style={{color: "red"}}>*</span></Form.Label>
                        <Form.Control type="text"
                                      name="topSpeed"
                                      placeholder="148"
                                      defaultValue={values.topSpeed}
                                      onChange={handleChange}
                                      onBlur={handleBlur}
                                      isValid={!errors.topSpeed}
                                      isInvalid={!!errors.topSpeed}/>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formTextareaGearbox">
                        <Form.Label>Gearbox<span style={{color: "red"}}>*</span></Form.Label>
                        <Form.Control type="text"
                                      name="gearbox"
                                      placeholder="5"
                                      defaultValue={values.gearbox}
                                      onChange={handleChange}
                                      onBlur={handleBlur}
                                      isValid={!errors.gearbox}
                                      isInvalid={!!errors.gearbox}/>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formTextareaFuelCapacity">
                        <Form.Label>Fuel capacity<span style={{color: "red"}}>*</span></Form.Label>
                        <Form.Control type="text"
                                      name="fuelCapacity"
                                      placeholder="10.0"
                                      defaultValue={values.fuelCapacity}
                                      onChange={handleChange}
                                      onBlur={handleBlur}
                                      isValid={!errors.fuelCapacity}
                                      isInvalid={!!errors.fuelCapacity}/>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formTextareaFuelConsumption">
                        <Form.Label>Fuel consumption<span style={{color: "red"}}>*</span></Form.Label>
                        <Form.Control type="text"
                                      name="fuelConsumption"
                                      placeholder="4.2"
                                      defaultValue={values.fuelConsumption}
                                      onChange={handleChange}
                                      onBlur={handleBlur}
                                      isValid={!errors.fuelConsumption}
                                      isInvalid={!!errors.fuelConsumption}/>
                    </Form.Group>


                    <div style={{display: "flex", justifyContent: "center"}}>
                        <Button variant="secondary" style={{paddingInline: "30px"}} type="submit">Update</Button>
                    </div>
                </Form>
            )}
        </Formik>
    )
}

export default MotorcycleModelUpdateModal;
