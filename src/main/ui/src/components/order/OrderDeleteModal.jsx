import {useEffect, useState} from "react";

import Button from "react-bootstrap/Button";

import CrudModal from "../shared/CrudModal";
import {fetchBackendApi} from "../../util/fetchBackendApi";


const OrderDeleteModal = (props) => {
    const modalBody = (
        <DeleteItemInformation orders={props.orders}
                               recordId={props.recordId}
                               setFormSubmit={props.setFormSubmit}
                               setDeleteModalShow={props.setDeleteModalShow}/>
    );

    return <CrudModal show={props.show} onHide={props.onHide} title="Delete Order" body={modalBody} />
}


const DeleteItemInformation = (props) => {
    const [currentRecord, setCurrentRecord] = useState({});


    useEffect(() => {
        const currentRecord = props.orders.find(o => o.id.toString() === props.recordId);
        setCurrentRecord(currentRecord !== undefined ? currentRecord : {});
    }, [props.orders, props.recordId])


    async function handleSubmit(event) {
        event.preventDefault();

        try {
            await fetchBackendApi(`/service/order/delete/${currentRecord.id}`, "DELETE");
            props.setFormSubmit(old => !old);
        } catch (error) {
            console.error("Unexpected error:", error.message);
        }
    }


    return (
        <>
            <h2 style={{textAlign: "center"}}>Are you sure to delete?</h2>

            <div className="delete-modal-container">
                <span>Order id</span>
                <h4>{currentRecord.id}</h4>
                <span>Order date</span>
                <h4>{currentRecord.orderDate}</h4>
                <span>Order status</span>
                <h4>{currentRecord.orderStatus}</h4>
                <span>Total amount</span>
                <h4>{currentRecord.totalAmount}</h4>
                <span>Motorcycle model name</span>
                <h4>{currentRecord.motorcycleModelName}</h4>
                <span>Customer full name</span>
                <h4>{currentRecord.customerFullName}</h4>
            </div>

            <Button type="button" variant="danger"
                    style={{margin: 0, position: "relative", left: "40%"}}
                    onClick={async (event) => {
                        await handleSubmit(event);
                        props.setDeleteModalShow(false)
                    }
            }>Delete</Button>
        </>
    )
}

export default OrderDeleteModal;
