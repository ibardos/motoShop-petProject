import Modal from 'react-bootstrap/Modal';


const CrudModal = (props) => {
    return (
        <Modal
            {...props}
            aria-labelledby="contained-modal-title-vcenter"
            centered
        >
            <div style={{paddingInline: "40px"}}>
                <Modal.Header closeButton>
                    <Modal.Title id="contained-modal-title-vcenter" className="w-100 text-center">
                        {props.title}
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {props.body}
                </Modal.Body>
            </div>
        </Modal>
    );
}

export default CrudModal;