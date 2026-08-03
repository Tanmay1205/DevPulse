import axiosInstance from "../api/axiosConfig";

export const getProfile = async () => {
    const response = await axiosInstance.get("/users/profile");
    return response.data;
};

export const updateProfile = async (data) => {
    const response = await axiosInstance.put("/users/profile", data);
    return response.data;
};

export const uploadProfileImage = async (file) => {

    const formData = new FormData();

    formData.append("file", file);

    const response = await axiosInstance.post(
        "/users/profile/image",
        formData,
        {
            headers: {
                "Content-Type": "multipart/form-data",
            },
        }
    );

    return response.data;
};

export const deleteAccount = async () => {
    return axiosInstance.delete("/users/account");
};