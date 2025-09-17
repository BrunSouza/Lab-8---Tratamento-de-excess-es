package br.ufpb.dcx.dsc.repositorios.services;

import br.ufpb.dcx.dsc.repositorios.api.ResourceNotFoundException;
import br.ufpb.dcx.dsc.repositorios.dto.UserDTO;
import br.ufpb.dcx.dsc.repositorios.models.Photo;
import br.ufpb.dcx.dsc.repositorios.models.User;
import br.ufpb.dcx.dsc.repositorios.repository.PhotoRepository;
import br.ufpb.dcx.dsc.repositorios.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, PhotoRepository photoRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.photoRepository = photoRepository;
        this.modelMapper = modelMapper;
    }

    public List<UserDTO> listAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
        return convertToDTO(user);
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        user.setNome(userDTO.getNome());
        user.setEmail(userDTO.getEmail());

        // Relacionamento com Photo
        if (userDTO.getPhoto() != null && userDTO.getPhoto().getPhotoId() != null) {
            Photo photo = photoRepository.findById(userDTO.getPhoto().getPhotoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Photo not found with ID: " + userDTO.getPhoto().getPhotoId()));
            user.setPhoto(photo);
        }

        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        existingUser.setNome(userDTO.getNome());
        existingUser.setEmail(userDTO.getEmail());

        // Relacionamento com Photo
        if (userDTO.getPhoto() != null && userDTO.getPhoto().getPhotoId() != null) {
            Photo photo = photoRepository.findById(userDTO.getPhoto().getPhotoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Photo not found with ID: " + userDTO.getPhoto().getPhotoId()));
            existingUser.setPhoto(photo);
        }

        User updatedUser = userRepository.save(existingUser);
        return convertToDTO(updatedUser);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = modelMapper.map(user, UserDTO.class);
        if (user.getPhoto() != null) {
            dto.setPhoto(user.getPhoto());
        }
        return dto;
    }
}