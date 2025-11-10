# GitHub Setup Guide

## Steps to Push to GitHub

### 1. Initialize Git Repository (if not already done)
```bash
git init
```

### 2. Add All Files
```bash
git add .
```

### 3. Create Initial Commit
```bash
git commit -m "Initial commit: Student Admission Portal with JSF and Hibernate"
```

### 4. Create Repository on GitHub
1. Go to https://github.com/new
2. Repository name: `student-admission-portal`
3. Description: `Student Admission Management System built with JSF and Hibernate ORM`
4. Choose Public or Private
5. **DO NOT** initialize with README, .gitignore, or license (we already have these)
6. Click "Create repository"

### 5. Add Remote and Push
```bash
# Add your GitHub repository as remote
git remote add origin https://github.com/YOUR_USERNAME/student-admission-portal.git

# Push to GitHub
git branch -M main
git push -u origin main
```

### 6. Verify
Visit your repository on GitHub to verify all files are uploaded correctly.

## Repository Settings

### Recommended GitHub Settings:
1. **Description**: "Student Admission Management System built with JSF and Hibernate ORM"
2. **Topics**: Add tags like:
   - `java`
   - `jsf`
   - `hibernate`
   - `maven`
   - `student-management`
   - `web-application`
3. **Website**: (Optional) If you deploy it
4. **License**: MIT (already included)

## Files Included in Repository

✅ **Included:**
- All source code (`src/` directory)
- `pom.xml` (Maven configuration)
- `README.md` (Main documentation)
- `LICENSE` (MIT License)
- `.gitignore` (Git ignore rules)
- `DEMO_PRESENTATION_SCRIPT.md` (Presentation script)
- `RUN_COMPLETE_SERVER.bat` (Server runner script)
- `CONTRIBUTING.md` (Contribution guidelines)

❌ **Excluded (via .gitignore):**
- `target/` (Build output)
- `website/` (Standalone HTML version)
- Extra documentation files
- IDE configuration files
- Compiled class files

## After Pushing

1. **Add a README badge** (optional):
   ```markdown
   ![Java](https://img.shields.io/badge/Java-8-orange)
   ![JSF](https://img.shields.io/badge/JSF-2.3-blue)
   ![Hibernate](https://img.shields.io/badge/Hibernate-5.6-green)
   ```

2. **Enable GitHub Pages** (optional):
   - Go to Settings → Pages
   - Source: `main` branch
   - Folder: `/docs` (if you create a docs folder)

3. **Add Topics** for better discoverability

## Next Steps

- Add screenshots to README
- Create releases for major versions
- Set up GitHub Actions for CI/CD (optional)
- Add more documentation as needed

---

**Your project is now ready for GitHub! 🚀**

