import {
    Box,
    Button,
    Typography,
    Paper,
    TextField,
    MenuItem,
    Stack,
    Chip,
} from "@mui/material";

import { DataGrid } from "@mui/x-data-grid";
import TaskDialog from "./TaskDialog";
import MainLayout from "../../components/layout/MainLayout";
import {
    getTasks,
    deleteTask,
} from "../../services/taskService";
import { useEffect, useState } from "react";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import IconButton from "@mui/material/IconButton";
import "./Tasks.css";



function Tasks() {
    const [open, setOpen] = useState(false);
    const [rows, setRows] = useState([]);
    const [keyword, setKeyword] = useState("");
    const [status, setStatus] = useState("");
    const [selectedTask, setSelectedTask] = useState(null);
    useEffect(() => {
        loadTasks();
    }, [keyword, status]);

    const loadTasks = async () => {
        try {
            const response = await getTasks(
                0,
                5,
                keyword,
                status
            );

            console.log("Full Response:", response);
            console.log("Content:", response.content);
            if (response.content?.length) {
                console.log(response.content[0]);
            }
            setRows(response.content || []);
        } catch (error) {
            console.error(error);
        }
    };

    const handleDelete = async (id) => {

        if (!window.confirm("Delete this task?")) {
            return;
        }

        try {

            await deleteTask(id);

            loadTasks();

        } catch (e) {

            console.log(e);

        }

    };

    const handleEdit = (task) => {

        setSelectedTask(task);

        setOpen(true);

    };

    const columns = [
        {
            field: "title",
            headerName: "Title",
            flex: 1,
            minWidth: 180,
        },

        {
            field: "description",
            headerName: "Description",
            flex: 2,
            minWidth: 250,
        },

        {
            field: "status",
            headerName: "Status",
            width: 150,

            renderCell: (params) => {

                const colors = {
                    TODO: "warning",
                    IN_PROGRESS: "info",
                    COMPLETED: "success",
                };

                return (
                    <Chip
                        className="status-chip"
                        label={params.value}
                        color={colors[params.value] || "default"}
                        size="small"
                    />
                );
            },
        },

        {
            field: "priority",
            headerName: "Priority",
            width: 130,

            renderCell: (params) => {

                const colors = {
                    LOW: "success",
                    MEDIUM: "warning",
                    HIGH: "error",
                };

                return (
                    <Chip
                        className="priority-chip"
                        label={params.value || "None"}
                        color={colors[params.value] || "default"}
                        size="small"
                    />
                );
            },
        },

        {
            field: "dueDate",
            headerName: "Due Date",
            width: 140,
        },

        {
            field: "actions",
            headerName: "Actions",
            width: 140,

            renderCell: (params) => (

                <>
                    <IconButton
                        color="primary"
                        onClick={() => handleEdit(params.row)}
                    >
                        <EditIcon />
                    </IconButton>

                    <IconButton
                        className="action-btn"
                        color="error"
                        onClick={() => handleDelete(params.row.id)}
                    >
                        <DeleteIcon />
                    </IconButton>
                </>

            )

        },
    ];

    return (

        <MainLayout>

            <Box>

                <Typography
                    className="page-title"
                    variant="h4"
                    mb={3}
                    fontWeight="bold">

                    Tasks

                </Typography>
                <Stack
                    direction="row"
                    spacing={2}
                    mb={3}
                >
                    <Box className="toolbar">

                        <TextField
                            className="search-box"
                            label="Search Task"
                            value={keyword}
                            onChange={(e) => setKeyword(e.target.value)}
                        />

                        <TextField
                            className="filter-box"
                            select
                            label="Status"
                            value={status}
                            onChange={(e) => setStatus(e.target.value)}
                        >
                            <MenuItem value="">All</MenuItem>
                            <MenuItem value="TODO">TODO</MenuItem>
                            <MenuItem value="IN_PROGRESS">IN PROGRESS</MenuItem>
                            <MenuItem value="COMPLETED">COMPLETED</MenuItem>
                        </TextField>

                        <Button
                            className="create-btn"
                            variant="contained"
                            onClick={() => {

                                setSelectedTask(null);

                                setOpen(true);

                            }}
                        >
                            + New Task
                        </Button>

                    </Box>
                    <TextField
                        className="search-box"
                        label="Search"
                        value={keyword}
                        onChange={(e) => setKeyword(e.target.value)}
                        sx={{ width: 300 }}
                    />

                    <TextField
                        className="filter-box"
                        select
                        label="Status"
                        value={status}
                        onChange={(e) => setStatus(e.target.value)}
                        sx={{ width: 180 }}
                    >
                        <MenuItem value="">All</MenuItem>
                        <MenuItem value="TODO">TODO</MenuItem>
                        <MenuItem value="IN_PROGRESS">IN PROGRESS</MenuItem>
                        <MenuItem value="COMPLETED">COMPLETED</MenuItem>
                    </TextField>



                </Stack>


                <Paper
                    elevation={3}
                    sx={{
                        width: "100%",
                        overflow: "hidden",
                        borderRadius: 2,
                    }}
                >
                    <TaskDialog
                        open={open}
                        task={selectedTask}
                        onClose={() => {

                            setOpen(false);

                            setSelectedTask(null);

                        }}
                        onSuccess={loadTasks}
                    />
                    <DataGrid
                        className="task-table"
                        rows={rows}
                        columns={columns}
                        getRowId={(row) => row.id}
                        pageSizeOptions={[5, 10, 20]}
                        initialState={{
                            pagination: {
                                paginationModel: {
                                    pageSize: 5,
                                    page: 0,
                                },
                            },
                        }}
                        disableRowSelectionOnClick
                        sx={{
                            border: 0,

                            "& .MuiDataGrid-columnHeaders": {
                                backgroundColor: "#f8fafc",
                                fontWeight: "bold",
                            },

                            "& .MuiDataGrid-row:hover": {
                                backgroundColor: "#f3f8ff",
                            },

                            "& .MuiDataGrid-cell": {
                                borderBottom: "1px solid #f1f5f9",
                            },

                            "& .MuiDataGrid-columnHeaderTitle": {
                                fontWeight: 700,
                            },
                        }}
                    />
                </Paper>

            </Box>

        </MainLayout>

    );
}

export default Tasks;