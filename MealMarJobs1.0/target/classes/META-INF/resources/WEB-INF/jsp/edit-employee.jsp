<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>


<div class="container">
    <h1>Edit Employee</h1>
    <form action="/update-employee" method="post">
        <input type="hidden" name="id" value="${employee.id}">
        <div class="form-group">
            <label for="name">Name:</label>
            <input type="text" class="form-control" id="name" name="name" value="${employee.name}" required>
        </div>
        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" class="form-control" id="email" name="email" value="${employee.email}" required>
        </div>
        <div class="form-group">
            <label for="phoneNumber">Phone Number:</label>
            <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" value="${employee.phoneNumber}" required>
        </div>
        <div class="form-group">
            <label for="position">Position:</label>
            <input type="text" class="form-control" id="position" name="position" value="${employee.position}" required>
        </div>
        <div class="form-group">
            <label for="coverLetter">Cover Letter:</label>
            <textarea class="form-control" id="coverLetter" name="coverLetter" rows="5" required>${employee.coverLetter}</textarea>
        </div>
        <div class="form-check" hidden>
            <input class="form-check-input" type="checkbox" id="cvChecked" name="cvChecked" ${employee.cvChecked ? 'checked' : ''}>
            <label class="form-check-label" for="cvChecked">CV Checked</label>
        </div>
        <div class="form-check">
            <input class="form-check-input" type="checkbox" id="agreeToTerms" name="agreeToTerms" ${employee.agreeToTerms ? 'checked' : ''} required>
            <label class="form-check-label" for="agreeToTerms">Agree to Terms</label>
        </div>
        <button type="submit" class="btn btn-primary">Save Changes</button>
    </form>
</div>




<%@ include file="common/footer.jspf"%>