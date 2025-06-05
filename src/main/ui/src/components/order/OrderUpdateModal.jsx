import {useEffect, useState} from "react";

import {Form} from "react-bootstrap";
import Button from "react-bootstrap/Button";

import CrudModal from "../shared/CrudModal";

import {fetchBackendApi} from "../../util/fetchBackendApi";

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
        const fetchOrder = async () => {
            try {
                const order = await fetchBackendApi(`/service/order/get/${props.recordId}`, "GET");
                setCurrentRecord(order);
            } catch (error) {
                console.error(`Failed to fetch Order with id: ${props.recordId} -`, error.message);
            }
        }

        const fetchOrderStatuses = async () => {
            try {
                const orderStatuses = await fetchBackendApi("/service/order/get/statuses", "GET");
                setOrderStatuses(orderStatuses);
            } catch (error) {
                console.error("Failed to fetch Order statuses:", error.message);
            }
        }

        fetchOrder();
        fetchOrderStatuses();
    }, [props.recordId])


    async function handleSubmit(values) {
        const body = {
            "id": currentRecord.id,
            "orderStatus": values.orderStatus ? values.orderStatus : currentRecord.orderStatus,
            "discount": values.discount ? values.discount : currentRecord.discount,
            "estimatedDeliveryDate": values.estimatedDeliveryDate ? values.estimatedDeliveryDate : currentRecord.estimatedDeliveryDate,
            "motorcycleStockId": currentRecord.motorcycleStockId,
            "customerId": currentRecord.customerId
        }

        try {
            await fetchBackendApi("/service/order/update", "PUT", body);
            props.setFormSubmit(old => !old);
        } catch (error) {
            console.error("Failed to update Order:", error.message);
        }
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
