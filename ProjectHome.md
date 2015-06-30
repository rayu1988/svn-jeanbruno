#O Projeto svn-jeanbruno no google code mantém uma estrutura de diretórios bem definida de projetos e suas documentações, é um servidor para os projetos pessoais porém de código aberto.<br />
#The svn-jeanbruno project at google code holds a well defined directory structure of projects and their documentations, is a server for personal projects but open source.

trunk<br />
#O tronco é a raiz dos diretórios<br />
#Is the root directory<br />

trunk-->DOCS<br />
#Diretório para manter documentações necessárias.<br />
#Directory to hold possible existing documentations.

trunk-->DOCS-->"MY PROJECT"<br />
#Cada documentação é relacionada a um projeto, logo para cada projeto há uma documentação.<br />
#Each documentation is concerning a project, thus for each project there is a documentation.

trunk-->APP<br />
#Diretório para manter os projetos (código fonte com a IDE ECLIPSE).<br />
#Directory to hold the projects (source code with the ECLIPSE IDE).

trunk-->APP-->"My Project"<br />
#Cada projeto é um diretório, e estes diretórios contém os projetos específicos do Eclipse.<br />
#Each projetct is a directory and these directories holds the specifics Eclipse projects.