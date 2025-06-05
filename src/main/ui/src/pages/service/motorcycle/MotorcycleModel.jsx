import {useContext, useEffect, useState} from "react";

import {useNavigate} from "react-router-dom";

import {FormControl} from "react-bootstrap";
import Container from "react-bootstrap/Container";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";

import StripedTable from "../../../components/shared/table/StripedTable";
import MotorcycleModelAddModal from "../../../components/motorcycleModel/MotorcycleModelAddModal";
import MotorcycleModelUpdateModal from "../../../components/motorcycleModel/MotorcycleModelUpdateModal";
import MotorcycleModelDeleteModal from "../../../components/motorcycleModel/MotorcycleModelDeleteModal";
import MotorcycleModelDeleteErrorModal from "../../../components/motorcycleModel/MotorcycleModelDeleteErrorModal";

import {fetchBackendApi} from "../../../util/fetchBackendApi";
import {removeJwtToken} from "../../../security/authService";
import {AuthenticationContext} from "../../../security/authenticationProvider";


const MotorcycleModel = () => {
    const navigate = useNavigate();

    const {userPermissions} = useContext(AuthenticationContext);

    // States for fetched data
    const [isLoaded, setIsLoaded] = useState(false);
    const [error, setError] = useState(null);
    const [motorcycleModels, setMotorcycleModels] = useState([]);
    const [filteredData, setFilteredData] = useState([]);

    // States for Modals
    const [addModalShow, setAddModalShow] = useState(false);
    const [updateModalShow, setUpdateModalShow] = useState(false);
    const [deleteModalShow, setDeleteModalShow] = useState(false);
    const [deleteErrorModalShow, setDeleteErrorModalShow] = useState(false);
    const [recordId, setRecordId] = useState("");

    // State for table re-render
    const [formSubmit, setFormSubmit] = useState(false);


    // Table live search feature
    function handleSearch(event) {
        setFilteredData(motorcycleModels.filter((item) =>
            item.id.toString().includes(event.target.value) ||
            item.manufacturer.name.toLowerCase().includes(event.target.value.toLowerCase()) ||
            item.modelName.toLowerCase().includes(event.target.value.toLowerCase()) ||
            item.modelYear.toString().includes(event.target.value) ||
            item.weight.toString().includes(event.target.value) ||
            item.displacement.toString().includes(event.target.value) ||
            item.horsePower.toString().includes(event.target.value) ||
            item.topSpeed.toString().includes(event.target.value) ||
            item.gearbox.toString().includes(event.target.value) ||
            item.fuelCapacity.toString().includes(event.target.value) ||
            item.fuelConsumptionPer100Kms.toString().includes(event.target.value) ||
            item.motorcycleModelType.toLowerCase().includes(event.target.value.toLowerCase())
        ));
    }


    // Fetch data for all the components of Motorcycle model page
    useEffect(() => {
        const fetchMotorcycleModels = async () => {
            try {
                const motorcycleModels = await fetchBackendApi("/service/motorcycle/model/get/all", "GET");
                setMotorcycleModels(motorcycleModels);
                setFilteredData(motorcycleModels);
            } catch (error) {
                if (error.status === 403) {
                    removeJwtToken();
                    navigate('/authentication/login');
                } else {
                    console.error("Unexpected error:", error.message);
                    setError(error);
                }
            } finally {
                setIsLoaded(true);
            }
        };

        fetchMotorcycleModels();
    }, [formSubmit, navigate])


    // Create a search bar for the "Table live search feature"
    const conditionalSearchBar = (
        <Col>
            <FormControl id="search-box" type="text" placeholder="Type here to filter..."
                         onChange={event => handleSearch(event)}/>
        </Col>
    )

    // Create a conditionally rendered "Add new item" button
    const conditionalAddButton = (userPermissions.includes('Create')) ? (
        <Col xs={2}>
            <Button variant="secondary" onClick={() => setAddModalShow(true)}>Add new item</Button>
        </Col>
    ) : null;

    // Compose the search bar and "Add new item" button into one conditionally rendered element
    const conditionalSearchBarAndAddButton = (!error) ? (
        <Container id="search-and-add-bar">
            <Row>
                {conditionalSearchBar}
                {conditionalAddButton}
            </Row>
        </Container>
    ) : null;


    return (
        <>
            <h2 className="page-title">Motorcycle models</h2>

            {conditionalSearchBarAndAddButton}

            <StripedTable originalData={motorcycleModels}
                          filteredData={filteredData}
                          setRecordId={setRecordId}
                          setUpdateModalShow={setUpdateModalShow}
                          setDeleteModalShow={setDeleteModalShow}
                          error={error}
                          isLoaded={isLoaded}
                          isSellButtonVisible={false}/>


            <MotorcycleModelAddModal setFormSubmit={setFormSubmit}
                                     show={addModalShow}
                                     setAddModalShow={setAddModalShow}
                                     onHide={() => setAddModalShow(false)}/>

            <MotorcycleModelUpdateModal motorcycleModels={motorcycleModels}
                                        recordId={recordId}
                                        setFormSubmit={setFormSubmit}
                                        show={updateModalShow}
                                        setUpdateModalShow={setUpdateModalShow}
                                        onHide={() => setUpdateModalShow(false)}/>

            <MotorcycleModelDeleteModal motorcycleModels={motorcycleModels}
                                        recordId={recordId}
                                        setErrorModalShow={setDeleteErrorModalShow}
                                        setFormSubmit={setFormSubmit}
                                        show={deleteModalShow} setDeleteModalShow={setDeleteModalShow}
                                        onHide={() => setDeleteModalShow(false)}/>

            <MotorcycleModelDeleteErrorModal motorcycleModels={motorcycleModels}
                                             recordId={recordId}
                                             show={deleteErrorModalShow}
                                             setDeleteErrorModalShow={setDeleteErrorModalShow}
                                             onHide={() => setDeleteErrorModalShow(false)}/>
        </>
    )
}


export default MotorcycleModel;
