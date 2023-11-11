package com.darglk.exposedpoc.service
import com.darglk.exposedpoc.controller.*
import com.darglk.exposedpoc.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
) : UserService {
    @Transactional
    override fun getUser(id: String): UserResponse {
        return userRepository.findUser(id)?.let {
            UserResponse(it.id.toString(), it.email)
        } ?: throw RuntimeException()
    }
    
    @Transactional
    override fun getUserDsl(id: String): UserResponse {
        return userRepository.findUserDsl(id)?.let {
            UserResponse(it.id.toString(), it.email)
        } ?: throw RuntimeException()
    }
    
    @Transactional
    override fun getUsers(search: String?, page: Int, pageSize: Int): List<UsersResponse> {
        return userRepository.getUsers(search, page, pageSize).map {
            UsersResponse(
                it.id.toString(), it.email,
                it.authorities.map {
                    AuthorityResponse(it.id.toString(), it.name)
                }
            )
        }
    }
    
    @Transactional
    override fun getUsersDsl(search: String?, page: Int, pageSize: Int): List<UsersResponse> {
        return userRepository.getUsersDsl(search, page, pageSize).map {
            UsersResponse(
                it.id.toString(), it.email,
                it.authorities.map {
                    AuthorityResponse(it.id.toString(), it.name)
                }
            )
        }
    }
    
    @Transactional
    override fun createUser(createUserRequest: CreateUserRequest): CreateUserResponse {
        val email = createUserRequest.email
        
        if (userRepository.doesEmailExist(email)) {
            throw java.lang.RuntimeException("Email in use")
        }
        
        val user = userRepository.insertUser(createUserRequest)
        return CreateUserResponse(
            user.id.toString(),
            user.email
        )
    }
    
    @Transactional
    override fun createUserDsl(createUserRequest: CreateUserRequest) {
        userRepository.insertUserDsl(createUserRequest)
    }
    
    @Transactional
    override fun doesUserExist(email: String): Boolean {
        return userRepository.findUserByEmail(email) != null
    }
    
    @Transactional
    override fun doesUserExistDsl(email: String): Boolean {
        return userRepository.findUserByEmailDsl(email) != null
    }
    
    @Transactional
    override fun updatePassword(request: UpdatePasswordRequest) {
        userRepository.updatePassword(request.userId, request.password)
    }
    
    @Transactional
    override fun updateEmail(request: UpdateEmailRequest) {
        userRepository.updateEmail(request.userId, request.email)
    }
    
    @Transactional
    override fun updateEmailDsl(request: UpdateEmailRequest) {
        userRepository.updateEmailDsl(request.userId, request.email)
    }
    
    @Transactional
    override fun updatePasswordDsl(request: UpdatePasswordRequest) {
        userRepository.updatePasswordDsl(request.userId, request.password)
    }
}