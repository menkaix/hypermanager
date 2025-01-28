//const projectCode = 'someProjectCode'; // Replace with actual project code logic

document.getElementById('upload-button').addEventListener('click', async () => {
    const fileInput = document.getElementById('file-input');
    const file = fileInput.files[0];
    const originalFile = file.name;

    if (file) {
        const formData = new FormData();
        formData.append('file', file);
        formData.append('projectCode', projectCode);
        formData.append('originalFile', originalFile);

        try {
            const response = await axios.post(`${base_url}/attachment/upload`, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                    
                },
                onUploadProgress: function(progressEvent) {
                    const percentCompleted = Math.round((progressEvent.loaded * 100) / progressEvent.total);
                    document.getElementById('upload-progress').style.width = percentCompleted + '%';
                }
            });
            M.toast({html: response.data});
        } catch (error) {
            M.toast({html: 'Failed to upload'});
        }
    } else {
        M.toast({html: 'Please select a file'});
    }
});