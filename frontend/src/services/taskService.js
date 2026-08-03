import axiosInstance from "../api/axiosConfig";

export const getTasks = async (
    page = 0,
    size = 5,
    keyword = "",
    status = ""
) => {

    let url = `/tasks?page=${page}&size=${size}`;

    if (keyword) url += `&keyword=${keyword}`;
    if (status) url += `&status=${status}`;

    const response = await axiosInstance.get(url);

    return response.data;
};

export const createTask = async (task) => {

    const response = await axiosInstance.post("/tasks", task);

    return response.data;
};

export const updateTask = async (id, task) => {

    const response = await axiosInstance.put(`/tasks/${id}`, task);

    return response.data;
};

export const deleteTask = async (id) => {

    await axiosInstance.delete(`/tasks/${id}`);

};

export const getRecentTasks = async () => {
    const response = await axiosInstance.get("/tasks?page=0&size=5");
    return response.data.content;
};
