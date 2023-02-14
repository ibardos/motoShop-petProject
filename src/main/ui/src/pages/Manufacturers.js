import {useEffect, useState} from "react";
import StripedTable from "../components/shared/table/StripedTable";

import {fetchData} from "../util/fetchData";


const Manufacturers = () => {
    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [manufacturers, setManufacturers] = useState([]);
    const [filteredData, setFilteredData] = useState([]);
    const [updateModalShow, setUpdateModalShow] = useState(false);
    const [deleteModalShow, setDeleteModalShow] = useState(false);
    const [recordId, setRecordId] = useState("");

    // Fetching data
    useEffect(() => {
        fetchData("manufacturer/get/all")
            .then(
                (result) => {
                    setIsLoaded(true);
                    setManufacturers(result);
                },
                (error) => {
                    setIsLoaded(true);
                    setError(error);
                }
            )
    }, [])

    // Check errors and show loading status
    if (error) {
        return <div>Error: {error.message}</div>;
    } else if (!isLoaded) {
        return <div>Loading...</div>;
    }

    return (
        <>
            <h2 className="pageTitle">Manufacturers</h2>
            <ManufacturersTable manufacturers={manufacturers} />
        </>
    )
};

            <StripedTable originalData={manufacturers} filteredData={filteredData} setRecordId={setRecordId}
                          setUpdateModalShow={setUpdateModalShow} setDeleteModalShow={setDeleteModalShow}/>


    )
}

export default Manufacturers;