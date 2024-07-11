import requests
import openpyxl

# List of employee IDs
employee_ids = [101, 102, 103, 104, 105]

# URL of the REST API (assuming it takes an employee ID as a parameter)
api_url = "https://api.example.com/employees/"

# Create a new Excel workbook and select the active worksheet
wb = openpyxl.Workbook()
ws = wb.active
ws.title = "Employee Details"

# Write the header row
headers = ["Employee ID", "Name", "Position", "Department"]
ws.append(headers)

def fetch_employee_details(employee_id):
    try:
        # Make a GET request to the API
        response = requests.get(f"{api_url}{employee_id}")
        
        # Check if the request was successful
        if response.status_code == 200:
            # Parse the JSON response
            employee_data = response.json()
            
            # Extract the required keys from the JSON response
            name = employee_data.get("name")
            position = employee_data.get("position")
            department = employee_data.get("department")
            
            # Write the extracted details to the Excel file
            ws.append([employee_id, name, position, department])
        else:
            print(f"Failed to fetch details for Employee ID: {employee_id}, Status Code: {response.status_code}")
    except requests.exceptions.RequestException as e:
        print(f"Error fetching details for Employee ID: {employee_id}: {e}")

# Loop through each employee ID and fetch details
for emp_id in employee_ids:
    fetch_employee_details(emp_id)

# Save the workbook to a file
wb.save("employee_details.xlsx")

print("Employee details have been written to employee_details.xlsx")
