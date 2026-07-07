import { useEffect, useState } from "react";
import API from "../services/api";

function Documents() {

    const [documents, setDocuments] = useState([]);

    const loadDocuments = async () => {
        try {
            const response = await API.get("/documents");
            setDocuments(response.data);
        } catch (error) {
            console.error(error);
        }
    };

    const deleteDocument = async (title) => {

        if (!window.confirm(`Delete "${title}"?`))
            return;

        await API.delete("/documents", {
            params: {
                title: title
            }
        });

        loadDocuments();
    };

    useEffect(() => {
        loadDocuments();
    }, []);

    return (

        <div className="card shadow p-4 mt-4">

            <h4>Uploaded Documents</h4>

            <table className="table table-striped mt-3">

                <thead>

                    <tr>
                        <th>Document</th>
                        <th>Chunks</th>
                        <th>Action</th>
                    </tr>

                </thead>

                <tbody>

                    {documents.map((doc, index) => (

                        <tr key={index}>

                            <td>{doc.title}</td>

                            <td>{doc.chunks}</td>

                            <td>

                                <button
                                    className="btn btn-danger btn-sm"
                                    onClick={() => deleteDocument(doc.title)}
                                >
                                    Delete
                                </button>

                            </td>

                        </tr>

                    ))}

                </tbody>

            </table>

        </div>

    );

}

export default Documents;