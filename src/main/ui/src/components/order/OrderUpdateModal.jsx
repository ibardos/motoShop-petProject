import {useEffect, useState} from "react";

import {Form} from "react-bootstrap";
import Button from "react-bootstrap/Button";

import CrudModal from "../shared/CrudModal";

import {fetchData} from "../../util/fetchData";
import {getJwtToken} from "../../security/authService";

// Imports related to form validation
import {Field, Formik} from "formik";
import * as Yup from "yup";


const OrderUpdateModal = (props) => {
    const modalBody = (
        <UpdateForm recordId={props.recordId}
                    setFormSubmit={props.setFormSubmit}
                    setUpdateModalShow={props.setUpdateModalShow}/>
    );

    return <CrudModal show={props.show} onHide={props.onHide} title="Update Order" body={modalBody} />
}


const UpdateForm = (props) => {
    const [orderStatuses, setOrderStatuses] = useState([]);
    const [currentRecord, setCurrentRecord] = useState(
    {
        "id": "",
        "orderStatus": "",
        "discount": "",
        "estimatedDeliveryDate": "",
        "motorcycleStockId": "",
        "customerId": ""
    })


    useEffect(() => {
        fetchData("/service/order/get/" + props.recordId)
            .then(result => setCurrentRecord(result));

        fetchData("/service/order/get/statuses")
            .then(result => setOrderStatuses(result));
    }, [props.recordId])


    async function handleSubmit(values) {
        const url = "/service/order/update";

        const requestBody = {
            "id": currentRecord.id,
            "orderStatus": values.orderStatus ? values.orderStatus : currentRecord.orderStatus,
            "discount": values.discount ? values.discount : currentRecord.discount,
            "estimatedDeliveryDate": values.estimatedDeliveryDate ? values.estimatedDeliveryDate : currentRecord.estimatedDeliveryDate,
            "motorcycleStockId": currentRecord.motorcycleStockId,
            "customerId": currentRecord.customerId
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
        orderStatus: Yup.string().required(),
        discount: Yup.number().required(),
        estimatedDeliveryDate: Yup.string().required()
    });


    return (
        <Formik
            enableReinitialize
            initialValues={{
                orderStatus: currentRecord.orderStatus,
                discount: currentRecord.discount,
                estimatedDeliveryDate: currentRecord.estimatedDeliveryDate
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
                    <Form.Group className="mb-3" controlId="formSelectOrderStatus">
                        <Form.Label>Order status</Form.Label>
                        <br/>
                        <Field as="select" name="orderStatus">
                            <option value={values.orderStatus}>{values.orderStatus}</option>
                            {orderStatuses.map(status => {
                                if (status !== values.orderStatus) {
                                    return <option key={status} value={status}>{status}</option>
                                }
                                return "PENDING"; // Needed for eslint checks in CI pipeline
                            })}
                        </Field>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formTextareaDiscount">
                        <Form.Label>Discount<span style={{color: "red"}}>*</span></Form.Label>
                        <Form.Control type="text"
                                      name="discount"
                                      placeholder="0.1"
                                      value={values.discount}
                                      onChange={handleChange}
                                      onBlur={handleBlur}
                                      isValid={touched.discount && !errors.discount}
                                      isInvalid={touched.discount && !!errors.discount}/>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formTextareaEstimatedDeliveryDate">
                        <Form.Label>Estimated delivery date<span style={{color: "red"}}>*</span></Form.Label>
                        <Form.Control type="date"
                                      name="estimatedDeliveryDate"
                                      placeholder="2025-08-26"
                                      value={values.estimatedDeliveryDate}
                                      onChange={handleChange}
                                      onBlur={handleBlur}
                                      isValid={touched.estimatedDeliveryDate && !errors.estimatedDeliveryDate}
                                      isInvalid={touched.estimatedDeliveryDate && !!errors.estimatedDeliveryDate}/>
                    </Form.Group>


                    <div style={{display: "flex", justifyContent: "center"}}>
                        <Button variant="secondary" style={{paddingInline: "30px"}} type="submit">Update</Button>
                    </div>
                </Form>
            )}
        </Formik>
    )
}

export default OrderUpdateModal;
