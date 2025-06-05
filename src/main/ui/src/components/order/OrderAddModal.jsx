import {useEffect, useState} from "react";

import {Form} from "react-bootstrap";
import Button from "react-bootstrap/Button";

import CrudModal from "../shared/CrudModal";

import {fetchBackendApi} from "../../util/fetchBackendApi";

// Imports related to form validation
import {Field, Formik} from "formik";
import * as Yup from "yup";
import {identifyCustomerId} from "../../util/identifyCustomerId";


const OrderAddModal = (props) => {
    const modalBody = (
        <AddForm motorcycleStocks={props.motorcycleStocks}
                 recordId={props.recordId}
                 setFormSubmit={props.setFormSubmit}
                 setOrderAddModalShow={props.setOrderAddModalShow}/>
    );

    return <CrudModal show={props.show} onHide={props.onHide} title="Create order" body={modalBody} />
}


const AddForm = (props) => {
    const [orderStatuses, setOrderStatuses] = useState([]);
    const [customers, setCustomers] = useState([]);
    const [currentMotorcycleStock, setCurrentMotorcycleStock] = useState({});

    const [incompleteCustomerAlert, setIncompleteCustomerAlert] = useState(false);


    useEffect(() => {
        const customers = props.motorcycleStocks.find(m => m.id.toString() === props.recordId);
        setCurrentMotorcycleStock(customers);

        const fetchOrderStatuses = async () => {
            try {
                const orderStatuses = await fetchBackendApi("/service/order/get/statuses", "GET");
                setOrderStatuses(orderStatuses);
            } catch (error) {
                console.error("Failed to fetch Order statuses:", error.message);
            }
        }

        const fetchCustomers = async () => {
            try {
                const customers = await fetchBackendApi("/service/customer/get/all", "GET");
                setCustomers(customers);
            } catch (error) {
                console.error("Failed to fetch Customers:", error.message);
            }
        }

        fetchOrderStatuses();
        fetchCustomers();
    }, [props.motorcycleStocks, props.recordId])


    async function handleSubmit(values) {
        const customerId = identifyCustomerId(values.customer)

        const body = {
            "orderStatus": values.orderStatus,
            "discount": values.discount,
            "estimatedDeliveryDate": values.estimatedDeliveryDate,
            "motorcycleStockId": currentMotorcycleStock.id,
            "customerId": customerId
        }

        try {
            await fetchBackendApi("/service/order/add", "POST", body);
            props.setFormSubmit(old => !old);
        } catch (error) {
            console.error("Failed to add Order:", error.message);
        }
    }


    const schema = Yup.object().shape({
        orderStatus: Yup.string().required(),
        discount: Yup.number().required(),
        estimatedDeliveryDate: Yup.string().required()
    });

    const initialCustomer = "Click to select Customer";
    const initialOrderStatus = "PENDING";

    return (
        <Formik
            enableReinitialize
            initialValues={{
                orderStatus: initialOrderStatus,
                discount: "",
                estimatedDeliveryDate: "",
                customer: initialCustomer
            }}
            validationSchema={schema}
            onSubmit={async values => {
                if (values.customer === initialCustomer) {
                    setIncompleteCustomerAlert(true)
                } else {
                    setIncompleteCustomerAlert(false)
                    await handleSubmit(values)
                    props.setOrderAddModalShow(false)
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
                    <Form.Group className="mb-3" controlId="formSelectCustomer">
                        <Form.Label>Customer<span style={{color: "red"}}>*</span></Form.Label>
                        <br/>
                        <Field as="select" name="customer">
                            <option value={values.customer}>{values.customer}</option>
                            {customers.map(c => {
                                if (c.fullName + " - id: " + c.id !== values.customer) {
                                    return <option key={c.fullName + " - id: " + c.id}
                                                   value={c.fullName + " - id: " + c.id}>{c.fullName + " - id: " + c.id}</option>
                                }
                                return "Click to select Customer"; // Needed for eslint checks in CI pipeline
                            })}
                        </Field>
                    </Form.Group>
                    <p className="formErrorText">{incompleteCustomerAlert ? "Choose a Customer!" : null}</p>

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

export default OrderAddModal;
