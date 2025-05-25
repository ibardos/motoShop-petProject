import {useEffect, useState} from "react";

import {Form} from "react-bootstrap";
import Button from "react-bootstrap/Button";

import CrudModal from "../shared/CrudModal";

import {fetchData} from "../../util/fetchData";
import {getJwtToken} from "../../security/authService";

// Imports related to form validation
import {Formik} from "formik";
import * as Yup from "yup";


const CustomerUpdateModal = (props) => {
    const modalBody = (
        <UpdateForm recordId={props.recordId}
                    setFormSubmit={props.setFormSubmit}
                    setUpdateModalShow={props.setUpdateModalShow}/>
    );

    return <CrudModal show={props.show} onHide={props.onHide} title="Update Customer" body={modalBody} />
}


const UpdateForm = (props) => {
    const [currentRecord, setCurrentRecord] = useState(
    {
        "id": "",
        "firstName": "",
        "lastName": "",
        "email": "",
        "phoneNumber": "",
        "address": "",
        "city": "",
        "postalCode": "",
        "country": ""
    })


    useEffect(() => {
        fetchData("/service/customer/get/updateDto/" + props.recordId)
            .then(result => setCurrentRecord(result));
    }, [props.recordId])


    async function handleSubmit(values) {
        const url = "/service/customer/update";

        const requestBody = {
            "id": currentRecord.id,
            "firstName": values.firstName ? values.firstName : currentRecord.firstName,
            "lastName": values.lastName ? values.lastName : currentRecord.lastName,
            "email": values.email ? values.email : currentRecord.email,
            "phoneNumber": values.phoneNumber ? values.phoneNumber : currentRecord.phoneNumber,
            "address": values.address ? values.address : currentRecord.address,
            "city": values.city ? values.city : currentRecord.city,
            "postalCode": values.postalCode ? values.postalCode : currentRecord.postalCode,
            "country": values.country ? values.country : currentRecord.country
        }

        const options = {
            method: "PUT",
            headers: { 'Content-Type': 'application/json', 'Authorization': `Bearer ${getJwtToken()}` },
            body: JSON.stringify(requestBody),
        }

        await fetch(url, options);
        props.setFormSubmit(old => !old);
    }


    const schema = Yup.object().shape({
        firstName: Yup.string().required(),
        lastName: Yup.string().required(),
        email: Yup.string().email().required(),
        phoneNumber: Yup.string().required(),
        address: Yup.string().required(),
        city: Yup.string().required(),
        postalCode: Yup.string().required(),
        country: Yup.string().required()
    });


    return (
        <Formik
            enableReinitialize
            initialValues={{
                firstName: currentRecord.firstName,
                lastName: currentRecord.lastName,
                email: currentRecord.email,
                phoneNumber: currentRecord.phoneNumber,
                address: currentRecord.address,
                city: currentRecord.city,
                postalCode: currentRecord.postalCode,
                country: currentRecord.country
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
                  touched,
                  errors,
              }) => (
                <Form onSubmit={handleSubmit}>
                    <Form.Group className="mb-3" controlId="formTextareaFirstName">
                        <Form.Label>First name<span style={{color: "red"}}>*</span></Form.Label>
                        <Form.Control type="text"
                                      name="firstName"
                                      placeholder="Ethan"
                                      value={values.firstName}
                                      onChange={handleChange}
                                      onBlur={handleBlur}
                                      isValid={touched.firstName && !errors.firstName}
                                      isInvalid={touched.firstName && !!errors.firstName}/>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formTextareaLastName">
                        <Form.Label>Last name<span style={{color: "red"}}>*</span></Form.Label>
                        <Form.Control type="text"
                                      name="lastName"
                                      placeholder="Hunt"
                                      value={values.lastName}
                                      onChange={handleChange}
                                      onBlur={handleBlur}
                                      isValid={touched.lastName && !errors.lastName}
                                      isInvalid={touched.lastName && !!errors.lastName}/>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formTextareaEmail">
                        <Form.Label>Email<span style={{color: "red"}}>*</span></Form.Label>
                        <Form.Control type="email"
                                      name="email"
                                      placeholder="ethan.hunt@imf.com"
                                      value={values.email}
                                      onChange={handleChange}
                                      onBlur={handleBlur}
                                      isValid={touched.email && !errors.email}
                                      isInvalid={touched.email && !!errors.email}/>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formTextareaPhoneNumber">
                        <Form.Label>Phone number<span style={{color: "red"}}>*</span></Form.Label>
                        <Form.Control type="text"
                                      name="phoneNumber"
                                      placeholder="+1 555 0137"
                                      value={values.phoneNumber}
                                      onChange={handleChange}
                                      onBlur={handleBlur}
                                      isValid={touched.phoneNumber && !errors.phoneNumber}
                                      isInvalid={touched.phoneNumber && !!errors.phoneNumber}/>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formTextareaAddress">
                        <Form.Label>Address<span style={{color: "red"}}>*</span></Form.Label>
                        <Form.Control type="text"
                                      name="address"
                                      placeholder="123 Spyglass Lane"
                                      value={values.address}
                                      onChange={handleChange}
                                      onBlur={handleBlur}
                                      isValid={touched.address && !errors.address}
                                      isInvalid={touched.address && !!errors.address}/>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formTextareaCity">
                        <Form.Label>City<span style={{color: "red"}}>*</span></Form.Label>
                        <Form.Control type="text"
                                      name="city"
                                      placeholder="Langley"
                                      value={values.city}
                                      onChange={handleChange}
                                      onBlur={handleBlur}
                                      isValid={touched.city && !errors.city}
                                      isInvalid={touched.city && !!errors.city}/>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formTextareaPostalCode">
                        <Form.Label>Postal code<span style={{color: "red"}}>*</span></Form.Label>
                        <Form.Control type="text"
                                      name="postalCode"
                                      placeholder="90210"
                                      value={values.postalCode}
                                      onChange={handleChange}
                                      onBlur={handleBlur}
                                      isValid={touched.postalCode && !errors.postalCode}
                                      isInvalid={touched.postalCode && !!errors.postalCode}/>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formTextareaCountry">
                        <Form.Label>Country<span style={{color: "red"}}>*</span></Form.Label>
                        <Form.Control type="text"
                                      name="country"
                                      placeholder="United States"
                                      value={values.country}
                                      onChange={handleChange}
                                      onBlur={handleBlur}
                                      isValid={touched.country && !errors.country}
                                      isInvalid={touched.country && !!errors.country}/>
                    </Form.Group>


                    <div style={{display: "flex", justifyContent: "center"}}>
                        <Button variant="secondary" style={{paddingInline: "30px"}} type="submit">Update</Button>
                    </div>
                </Form>
            )}
        </Formik>
    )
}

export default CustomerUpdateModal;
