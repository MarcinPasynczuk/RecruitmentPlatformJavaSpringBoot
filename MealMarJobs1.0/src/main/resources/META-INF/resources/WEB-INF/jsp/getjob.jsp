<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>


<style>
.form-group {
	  margin-bottom: 2rem;
	}

	.form-check {
	  margin-bottom: 2rem;
	}
</style>

<div class="container">
  <h2 class="my-4">Application Form</h2>
  <form method="POST" action="/form" enctype="multipart/form-data">
    <div class="form-group">
      <label for="inputName">Name</label>
      <input type="text" class="form-control" id="inputName" name="name" placeholder="Enter your name" required>
      <span class="text-danger">${errors.name}</span>
    </div>
    <div class="form-group">
      <label for="inputEmail">Email address</label>
      <input type="email" class="form-control" id="inputEmail" name="email" placeholder="Enter your email" required>
      <span class="text-danger">${errors.email}</span>
    </div>
    <div class="form-group">
      <label for="inputPhone">Phone Number</label>
      <input type="tel" class="form-control" id="inputPhone" name="phoneNumber" placeholder="Enter your phone number" required>
      <span class="text-danger">${errors.phoneNumber}</span>
    </div>
    
    
<div class="form-group">
      <label for="inputPosition">Position</label>
      <input type="text" class="form-control" id="inputPosition" name="position" placeholder="Enter the position you are applying for" required>
      <span class="text-danger">${errors.position}</span>
    </div>
    <div class="form-group">
      <label for="inputResume">Resume/CV</label>
      <input type="file" class="form-control" id="inputResume" name="resume" accept=".pdf" required>
      <span class="text-danger">${errors.resume}</span>
    </div>
    <div class="form-group">
      <label for="inputCoverLetter">Cover Letter</label>
      <textarea class="form-control" id="inputCoverLetter" name="coverLetter" rows="3" required></textarea>
      <span class="text-danger">${errors.coverLetter}</span>
    </div>
    
    
  <div class="form-check">
      <input type="checkbox" class="form-check-input" id="checkTerms" name="agreeToTerms" required>
      <label class="form-check-label" for="checkTerms">I agree to the terms and conditions</label>
    <span class="text-danger">${errors.agreeToTerms}</span>
    </div>
    
<div class="form-check" style="display:none;">
  <input type="checkbox" class="form-check-input" id="checkTerms" name="cvChecked">
  <label class="form-check-label" for="checkTerms">CV checked</label>
</div>

<div class="form-check" style="display:none;">
  <input type="checkbox" class="form-check-input" id="cvMovedNextStage" name="cvMovedNextStage">
  <label class="form-check-label" for="checkTerms">CV Moved</label>
</div>
    
    <button type="submit" class="btn btn-primary my-4" name="submit" value="submit">Submit</button>
  </form>
</div>




<%@ include file="common/footer.jspf"%>