<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>


<div>
    <h1>CV List</h1>
    <table class="table table-striped">
        <thead>
            <tr>
                <th>Name</th>
                <th>Email</th>
                <th>Phone Number</th>
                <th>Position</th>
                <th>Cover Letter</th>
                <th>CV</th>
                <th>Agree to Terms</th>
                <th>CV Checked</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${employees}" var="employee">
                <tr>
                    <td>${employee.name}</td>
                    <td>${employee.email}</td>
                    <td>${employee.phoneNumber}</td>
                    <td>${employee.position}</td>
                    <td>
                        <a href="download-cover-letter/${employee.id}" class="btn btn-info">Download Letter</a>
                    </td>
                                        <td>
                         <a href="download-resume/${employee.id}" class="btn btn-info">Download CV</a>
                    </td>
                    <td>${employee.agreeToTerms ? 'Yes' : 'No'}</td>
                    <td>
                        <span style="${employee.cvChecked ? 'color: green;' : 'color: red;'}">${employee.cvChecked ? 'Yes' : 'No'}</span>
                    </td>
                    <td>
                        <a href="check-cv?id=${employee.id}" class="btn btn-primary">Check CV</a>
                        <a href="delete-employee?id=${employee.id}" class="btn btn-danger" onclick="return confirm('Are you sure you want to delete this employee?')">Delete</a>
                       
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>




<%@ include file="common/footer.jspf"%>